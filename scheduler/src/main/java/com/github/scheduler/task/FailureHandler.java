package com.github.scheduler.task;

import com.github.scheduler.task.schedule.Schedule;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:38 PM
 */
public interface FailureHandler<T> {

    void onFailure(ExecutionComplete executionComplete, ExecutionOperations<T> executionOperations);

    class OnFailureReschedule<T> implements FailureHandler<T>{
        private final Schedule schedule;
        public OnFailureReschedule(Schedule schedule){
            this.schedule = schedule;
        }

        @Override
        public void onFailure(ExecutionComplete executionComplete, ExecutionOperations<T> executionOperations) {

        }
    }
}
