package com.housekeeper.core.util;

/**
 * @author yezy
 * @since 2018/6/29
 */
public class StopWatch {

    private long start;

    public StopWatch() {
        reset();
    }

    public void reset() {
        start = System.currentTimeMillis();
    }

    public long elapsedTime() {
        long end = System.currentTimeMillis();
        return end - start;
    }
}
