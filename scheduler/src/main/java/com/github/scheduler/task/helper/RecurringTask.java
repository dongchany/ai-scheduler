package com.github.scheduler.task.helper;

import com.github.scheduler.Clock;
import com.github.scheduler.Scheduler;
import com.github.scheduler.task.*;
import com.github.scheduler.task.schedule.Schedule;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 4:13 PM
 */
public abstract class RecurringTask<T> extends Task<T> implements OnStartup{

    public static final String INSTANCE = "recurring";
    private final CompletionHandler.OnCompleteReschedule<T> onComplete;
    private ScheduleOnStartup<T> scheduleOnStartup;

    public RecurringTask(String name, Schedule schedule, Class<T> dataClass){
        this(name, schedule, dataClass, new ScheduleRecurringOnStartup<>(INSTANCE, null, schedule), new FailureHandler.OnFailureReschedule<>(schedule), new DeadExecutionHandler.ReviveDeadExecution<>());
    }

    public RecurringTask(String name, Schedule schedule, Class<T> dataClass, T initialData){
        this(name, schedule, dataClass, new ScheduleRecurringOnStartup<>(INSTANCE, initialData, schedule), new FailureHandler.OnFailureReschedule<>(schedule), new DeadExecutionHandler.ReviveDeadExecution<>());
    }

    public RecurringTask(String name, Schedule schedule, Class<T> dataClass, ScheduleRecurringOnStartup<T> scheduleOnStartup, FailureHandler<T> failureHandler, DeadExecutionHandler<T> deadExecutionHandler){
       super(name, dataClass, failureHandler, deadExecutionHandler);
       this.onComplete = new CompletionHandler.OnCompleteReschedule<>(schedule);
       this.scheduleOnStartup = scheduleOnStartup;
    }

    @Override
    public void onStartup(Scheduler scheduler, Clock clock) {
        if (scheduleOnStartup != null){
            scheduleOnStartup.apply(scheduler, clock, this);
        }
    }

    @Override
    public CompletionHandler<T> execute(TaskInstance<T> taskInstance, ExecutionContext executionContext) {
        executeRecurringly(taskInstance,executionContext);
        return onComplete;
    }

    public abstract void executeRecurringly(TaskInstance<T> taskInstance, ExecutionContext executionContext);
}
