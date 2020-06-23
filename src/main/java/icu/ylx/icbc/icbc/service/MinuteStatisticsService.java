package icu.ylx.icbc.icbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.ylx.icbc.icbc.constant.StatisticalPeriod;
import icu.ylx.icbc.icbc.entity.MinuteStatistics;
import icu.ylx.icbc.icbc.entity.Statistics;
import java.time.temporal.TemporalAmount;

public interface MinuteStatisticsService extends IService<MinuteStatistics> {

    /**
     * 根据周期进行数据统计
     *
     * @param minuteStatistics 某一分钟的交易信息统计数据，至少需要分行好，支行号，时间戳
     * @param statisticalPeriod 统计周期，可以按小时，按日，按月进行统计
     * @return 计算完成的统计数据
     */
    Statistics countByPeriod(MinuteStatistics minuteStatistics, StatisticalPeriod statisticalPeriod);
}
