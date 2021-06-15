package com.excilys.aop;

import java.time.Clock;

public class StopWatch {
    private final Clock clock;
    private long startTimeMillis;

    public StopWatch(Clock clock) {
        this.clock = clock;
    }

    public void start() {
        startTimeMillis = clock.millis();
    }

    public long elapsed() {
        return clock.millis() - startTimeMillis;
    }
}