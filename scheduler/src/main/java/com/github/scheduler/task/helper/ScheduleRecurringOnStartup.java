package com.github.scheduler.task.helper;

import com.github.scheduler.Clock;
import com.github.scheduler.Scheduler;
import com.github.scheduler.task.Task;
import com.github.scheduler.task.TaskInstance;
import com.github.scheduler.task.schedule.Schedule;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:33 PM
 */
@Slf4j
public class ScheduleRecurringOnStartup<T> implements ScheduleOnStartup<T>{
    private final Schedule schedule;
    private final String instance;
    private final T data;

    ScheduleRecurringOnStartup(String instance, T data, Schedule schedule){
        this.instance = instance;
        this.data = data;
        this.schedule = schedule;
    }

    @Override
    public void apply(Scheduler scheduler, Clock clock, Task<T> task) {
        final TaskInstance<T> instanceWithoutData = task.instance(this.instance);

        // No preexisting execution, create initial one
        final Instant initialExecutionTime = schedule.getInitialExecutionTime(clock.now());
        log.info("Creating initial execution for task-instance '{}'. Next execution-time: {}", instanceWithoutData, initialExecutionTime);
        scheduler.schedule(getSchedulableInstance(task), initialExecutionTime);
    }
}
