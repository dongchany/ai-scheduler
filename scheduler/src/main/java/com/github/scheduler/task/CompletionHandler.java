package com.github.scheduler.task;

import com.github.scheduler.task.schedule.Schedule;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:40 PM
 */
public interface CompletionHandler<T> {
    void complete(ExecutionComplete executionComplete, ExecutionOperations<T> executionOperations);

    class OnCompleteReschedule<T> implements CompletionHandler<T>{
        private final Schedule schedule;
        public OnCompleteReschedule(Schedule schedule){
            this.schedule = schedule;
        }

        @Override
        public void complete(ExecutionComplete executionComplete, ExecutionOperations<T> executionOperations) {

        }
    }

    class OnCompleteRemove<T> implements CompletionHandler<T> {

        @Override
        public void complete(ExecutionComplete executionComplete, ExecutionOperations<T> executionOperations) {
            executionOperations.stop();
        }
    }

}
