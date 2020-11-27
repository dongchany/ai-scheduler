package com.github.dongchan.scheduler.task.schedule;

import java.time.Duration;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 4:30 PM
 */
public class Schedules {
    public static Schedule fixedDelay(Duration delay){
        return FixedDelay.of(delay);
    }
}
