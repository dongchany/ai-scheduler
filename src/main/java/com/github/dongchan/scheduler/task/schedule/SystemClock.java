package com.github.dongchan.scheduler.task.schedule;

import com.github.dongchan.scheduler.Clock;

import java.time.Instant;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 2:03 PM
 */
public class SystemClock implements Clock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
