package com.github.scheduler.task.helper;

import com.github.scheduler.Clock;
import com.github.scheduler.Scheduler;
import com.github.scheduler.task.Task;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:34 PM
 */
public interface ScheduleOnStartup<T> {
    void apply(Scheduler scheduler, Clock clock, Task<T> task);
}
