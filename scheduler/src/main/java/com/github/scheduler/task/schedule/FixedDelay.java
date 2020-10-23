package com.github.scheduler.task.schedule;

import java.time.Duration;
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
}
