package com.github.dongchan.scheduler.task.helper;

import com.github.dongchan.scheduler.task.*;
import com.github.scheduler.task.*;
import com.github.dongchan.scheduler.task.schedule.Schedule;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 4:22 PM
 */
public class Tasks {

    public static RecurringTaskBuilder<Void> recurring(String name, Schedule schedule){
        return new RecurringTaskBuilder<Void>(name, schedule, Void.class);
    }
    public static <T> RecurringTaskBuilder<T> recurring(String name, Schedule schedule, Class<T> dataClass) {
        return new RecurringTaskBuilder<>(name, schedule, dataClass);
    }

    public static class RecurringTaskBuilder<T>{
        private final String name;
        private final Schedule schedule;
        private Class<T> dataClass;
        private FailureHandler<T> onFailure;
        private DeadExecutionHandler<T> onDeadExecution;
        private ScheduleRecurringOnStartup<T> scheduleOnStartup;

        public RecurringTaskBuilder(String name, Schedule schedule, Class<T> dataClass){
            this.name = name;
            this.schedule = schedule;
            this.dataClass = dataClass;
            this.onFailure = new FailureHandler.OnFailureReschedule<>(schedule);
            this.onDeadExecution = new DeadExecutionHandler.ReviveDeadExecution<>();
            this.scheduleOnStartup = new ScheduleRecurringOnStartup<>(RecurringTask.INSTANCE, null, schedule);
        }

        public RecurringTaskBuilder<T> initialData(T initialData) {
            this.scheduleOnStartup = new ScheduleRecurringOnStartup<>(RecurringTask.INSTANCE, initialData, schedule);
            return this;
        }

        public RecurringTaskBuilder<T> onFailureReschedule(){
            this.onFailure = new FailureHandler.OnFailureReschedule<>(schedule);
            return this;
        }

        public RecurringTaskBuilder<T> onDeadExecutionRevive(){
            this.onDeadExecution = new DeadExecutionHandler.ReviveDeadExecution<>();
            return this;
        }

        public RecurringTask<T> execute(VoidExecutionHandler<T> executionHandler){
            return new RecurringTask(name, schedule, dataClass, scheduleOnStartup, onFailure, onDeadExecution){

                @Override
                public void executeRecurringly(TaskInstance taskInstance, ExecutionContext executionContext) {
                    executionHandler.execute(taskInstance, executionContext);
                }
            };
        }

    }
}
