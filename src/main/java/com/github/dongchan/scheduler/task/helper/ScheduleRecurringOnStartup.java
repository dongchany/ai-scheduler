package com.github.dongchan.scheduler.task.helper;

import com.github.dongchan.scheduler.Clock;
import com.github.dongchan.scheduler.Scheduler;
import com.github.dongchan.scheduler.task.Task;
import com.github.dongchan.scheduler.task.TaskInstance;
import com.github.dongchan.scheduler.task.schedule.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:33 PM
 */
public class ScheduleRecurringOnStartup<T> implements ScheduleOnStartup<T> {
    private static final Logger log = LoggerFactory.getLogger(ScheduleRecurringOnStartup.class);
    private final Schedule schedule;
    private final String instance;
    private final T data;

    ScheduleRecurringOnStartup(String instance, T data, Schedule schedule) {
        this.instance = instance;
        this.data = data;
        this.schedule = schedule;
    }

    @Override
    public void apply(Scheduler scheduler, Clock clock, Task<T> task) {
        final TaskInstance<T> instanceWithoutData = task.createInstance(this.instance);

        // No preexisting execution, create initial one
        final Instant initialExecutionTime = schedule.getInitialExecutionTime(clock.now());
        log.info("Creating initial execution for task-instance '{}'. Next execution-time: {}", instanceWithoutData, initialExecutionTime);
        scheduler.schedule(getSchedulableInstance(task), initialExecutionTime);
    }

    private TaskInstance<T> getSchedulableInstance(Task<T> task) {
        if (data == null) {
            return task.createInstance(instance);
        } else {
            return task.createInstance(instance, data);
        }
    }
}
