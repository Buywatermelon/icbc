package icu.ylx.icbc.icbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.ylx.icbc.icbc.constant.StatisticalPeriod;
import icu.ylx.icbc.icbc.entity.MinuteStatistics;
import icu.ylx.icbc.icbc.entity.Statistics;
import icu.ylx.icbc.icbc.mapper.MinuteStatisticsMapper;
import icu.ylx.icbc.icbc.service.MinuteStatisticsService;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MinuteStatisticsServiceImpl extends ServiceImpl<MinuteStatisticsMapper, MinuteStatistics> implements MinuteStatisticsService {

    @Override
    public Statistics countByPeriod(MinuteStatistics minuteStatistics, StatisticalPeriod statisticalPeriod) {
        LambdaQueryWrapper<MinuteStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MinuteStatistics::getBranch, minuteStatistics.getBranch())
                .eq(MinuteStatistics::getSubBranch, minuteStatistics.getSubBranch())
                .between(MinuteStatistics::getTimeStamp,
                        minuteStatistics.getTimeStamp(), minuteStatistics.getTimeStamp().plus(statisticalPeriod.getPeriod()));

        List<MinuteStatistics> minuteStatisticsList = this.list(queryWrapper);

        List<Integer> transactionNumberList = minuteStatisticsList.parallelStream()
                .map(MinuteStatistics::getTransactionNumber)
                .collect(Collectors.toList());

        // 总交易量
        Integer totalTransactionNumber = transactionNumberList.parallelStream()
                .reduce(0, Integer::sum);

        // 峰值交易量
        Integer peckTransactionNumber = transactionNumberList.parallelStream()
                .reduce(Integer::max).get();

        // 总交易额
        Integer totalTransactionAmount = minuteStatisticsList.parallelStream()
                .map(ms -> ms.getAverage() * ms.getTransactionNumber())
                .reduce(0, Integer::sum);

        // 平均交易额，保留小数点后两位
        DecimalFormat df = new DecimalFormat("0.00");
        String averageTransactionAmount = df.format((float) totalTransactionAmount / totalTransactionNumber);

        return Statistics.builder()
                .branch(minuteStatistics.getBranch())
                .subBranch(minuteStatistics.getSubBranch())
                .cycleFlag(statisticalPeriod.getCycleFlag())
                .timeStamp(minuteStatistics.getTimeStamp())
                .total(totalTransactionNumber)
                .peak(peckTransactionNumber)
                .average(averageTransactionAmount)
                .build();
    }
}
