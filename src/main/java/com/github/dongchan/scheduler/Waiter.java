package com.github.dongchan.scheduler;


import java.time.Duration;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 3:35 PM
 */
public class Waiter {

    private final Duration duration;
    private final Object lock;
    private Clock clock;

    public Waiter(Duration duration, Clock clock) {
        this(duration, clock, new Object());
    }

    public Waiter(Duration duration, Clock clock, Object lock) {
        this.duration = duration;
        this.clock = clock;
        this.lock = lock;
    }

    public Duration getDuration() {
        return duration;
    }

    public Clock getClock() {
        return clock;
    }

    public Object getLock() {
        return lock;
    }

    public void doWait() throws InterruptedException {
    }

}
