package com.excilys.aop;

import java.time.Clock;

import org.apache.log4j.Logger;

public class TimedInvocation {
    private static final Logger LOG = Logger.getLogger(TimedInvocation.class);

    public <T> T invoke(InvocationPoint<T> invocationPoint) throws Exception {
        StopWatch watch = new StopWatch(Clock.systemDefaultZone());
        watch.start();
        LOG.info("===================== Start calculation =====================");
        T result = invocationPoint.invoke();
        LOG.info("===================== End calculation ({} ms) " + watch.elapsed() + " =====================");
        return result;
    }
}