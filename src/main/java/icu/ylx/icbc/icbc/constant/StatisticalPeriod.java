package icu.ylx.icbc.icbc.constant;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public enum StatisticalPeriod {

    HOUR(1, Duration.ofHours(1)),

    DAY(2, Duration.ofDays(1)),

    MONTH(3, Period.ofMonths(1));

    private int cycleFlag;

    private TemporalAmount period;

    StatisticalPeriod(int cycleFlag, TemporalAmount period) {
        this.cycleFlag = cycleFlag;
        this.period = period;
    }

    public int getCycleFlag() {
        return cycleFlag;
    }

    public void setCycleFlag(int cycleFlag) {
        this.cycleFlag = cycleFlag;
    }

    public TemporalAmount getPeriod() {
        return period;
    }

    public void setPeriod(TemporalAmount period) {
        this.period = period;
    }
}
