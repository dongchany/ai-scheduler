package com.github.dongchan.scheduler.concurrent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 6:13 PM
 */
public abstract class LoggingRunnable implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(LoggingRunnable.class);

    public abstract void runButLogExceptions();

    @Override
    public void run() {
        try {
            runButLogExceptions();
        } catch (Exception e) {
            log.error("Unexcepted exception when executing Runnable", e);
        }
    }
}
