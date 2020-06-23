package icu.ylx.icbc.icbc.controller;

import icu.ylx.icbc.icbc.constant.StatisticalPeriod;
import icu.ylx.icbc.icbc.entity.MinuteStatistics;
import icu.ylx.icbc.icbc.entity.Statistics;
import icu.ylx.icbc.icbc.service.MinuteStatisticsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class StatisticsController {

    private final MinuteStatisticsService minuteStatisticsService;

    public StatisticsController(MinuteStatisticsService minuteStatisticsService) {
        this.minuteStatisticsService = minuteStatisticsService;
    }

    @GetMapping("/hour")
    public Statistics countByHour(
            @RequestParam(required = false) String transactionInformation,
            @RequestParam(required = false) LocalDateTime timestamp,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String subBranch
    ) {
        if (!StringUtils.isEmpty(transactionInformation)) {
            return minuteStatisticsService.countByPeriod(
                    StatisticsController.parseTransactionInformation(transactionInformation),
                    StatisticalPeriod.HOUR
            );
        }

        return minuteStatisticsService.countByPeriod(
                MinuteStatistics.builder()
                        .timeStamp(timestamp)
                        .branch(branch)
                        .subBranch(subBranch)
                        .build(),
                StatisticalPeriod.HOUR
        );
    }

    @GetMapping("/day")
    public Statistics countByDay(
            @RequestParam(required = false) String transactionInformation,
            @RequestParam(required = false) LocalDateTime timestamp,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String subBranch
    ) {
        if (!StringUtils.isEmpty(transactionInformation)) {
            return minuteStatisticsService.countByPeriod(
                    StatisticsController.parseTransactionInformation(transactionInformation),
                    StatisticalPeriod.DAY
            );
        }

        return minuteStatisticsService.countByPeriod(
                MinuteStatistics.builder()
                        .timeStamp(timestamp)
                        .branch(branch)
                        .subBranch(subBranch)
                        .build(),
                StatisticalPeriod.DAY
        );
    }

    @GetMapping("/month")
    public Statistics countByMonth(
            @RequestParam(required = false) String transactionInformation,
            @RequestParam(required = false) LocalDateTime timestamp,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String subBranch
    ) {
        if (!StringUtils.isEmpty(transactionInformation)) {
            return minuteStatisticsService.countByPeriod(
                    StatisticsController.parseTransactionInformation(transactionInformation),
                    StatisticalPeriod.MONTH
            );
        }

        return minuteStatisticsService.countByPeriod(
                MinuteStatistics.builder()
                        .timeStamp(timestamp)
                        .branch(branch)
                        .subBranch(subBranch)
                        .build(),
                StatisticalPeriod.MONTH
        );
    }

    private static MinuteStatistics parseTransactionInformation(String transactionInformation) {
        String[] infos = transactionInformation.split(",");
        // 解析时间戳
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime tradingMinutes = LocalDateTime.from(pattern.parse(infos[0]));

        // 解析银行编码
        String bankNumber = infos[1];
        String branchNumber = bankNumber.substring(0, 3);
        String subBranchNumber = bankNumber.substring(3, 8);

        return MinuteStatistics.builder()
                .timeStamp(tradingMinutes) // 时间戳
                .branch(branchNumber) // 分行号
                .subBranch(subBranchNumber) // 支行号
                .transactionNumber(Integer.parseInt(infos[2])) // 交易笔数
                .average((Integer.parseInt(infos[3]))) // 平均交易额
                .build();
    }
}
