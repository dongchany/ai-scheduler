package com.github.scheduler.task.schedule;

import com.github.scheduler.task.ExecutionComplete;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 4:36 PM
 */
public class FixedDelay implements Schedule {
    private final Duration duration;
    public FixedDelay(Duration duration) {
        this.duration = Objects.requireNonNull(duration);
    }

    public static FixedDelay of(Duration duration){
        return new FixedDelay(duration);
    }

    @Override
    public Instant getNextExecutionTime(ExecutionComplete executionComplete) {
        return null;
    }

    @Override
    public Instant getInitialExecutionTime(Instant now) {
        return now;
    }
}
