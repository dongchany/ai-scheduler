package com.github.scheduler.testhelper;

import com.github.scheduler.Clock;

import java.time.Instant;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:22 PM
 */
public class SettableClock implements Clock {

    public Instant now = Instant.now();

    public void setNow(Instant now) {
        this.now = now;
    }

    @Override
    public Instant now() {
        return now;
    }
}
