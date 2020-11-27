package com.github.dongchan.scheduler;

import lombok.Getter;

import java.time.Duration;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 3:35 PM
 */
@Getter
public class Waiter {

    private final Duration duration;
    private Clock clock;
    private final Object lock;

    public Waiter(Duration duration, Clock clock) {
        this(duration, clock, new Object());
    }

    public Waiter(Duration duration, Clock clock, Object lock) {
        this.duration = duration;
        this.clock = clock;
        this.lock = lock;
    }

    public void doWait() throws InterruptedException{
    }

}
