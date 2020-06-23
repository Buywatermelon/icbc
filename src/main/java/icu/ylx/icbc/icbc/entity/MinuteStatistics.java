package icu.ylx.icbc.icbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("minute_statistics")
public class MinuteStatistics {

    // 时间戳
    private LocalDateTime timeStamp;

    // 支行号
    private String branch;

    //分行好
    private String subBranch;

    // 交易笔数
    private int transactionNumber;

    // 平均交易金额
    private int average;
}
