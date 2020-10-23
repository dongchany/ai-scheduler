package com.github.scheduler.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 6:13 PM
 */
@Slf4j
public abstract class LoggingRunnable implements Runnable {
    public abstract void runButLogExceptions();

    @Override
    public void run() {
        try {
            runButLogExceptions();
        }catch (Exception e){
            log.error("Unexcepted exception when executing Runnable", e);
        }
    }
}
