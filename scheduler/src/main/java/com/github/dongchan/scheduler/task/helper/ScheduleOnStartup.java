package com.github.dongchan.scheduler.task.helper;

import com.github.dongchan.scheduler.Clock;
import com.github.dongchan.scheduler.Scheduler;
import com.github.dongchan.scheduler.task.Task;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:34 PM
 */
public interface ScheduleOnStartup<T> {
    void apply(Scheduler scheduler, Clock clock, Task<T> task);
}
