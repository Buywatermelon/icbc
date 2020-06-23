package icu.ylx.icbc.icbc.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Statistics {

    // 时间戳
    private LocalDateTime timeStamp;

    // 统计周期标志，小时为1，天为2，月为3
    private int cycleFlag;

    // 分行号
    private String branch;

    // 支行号
    private String subBranch;

    // 总交易量
    private int total;

    // 峰值交易量，即在统计周期内分钟最大交易量
    private int peak;

    // 平均交易金额
    private String average;
}
