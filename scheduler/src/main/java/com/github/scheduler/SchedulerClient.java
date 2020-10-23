package com.github.scheduler;

import com.github.scheduler.dao.TaskRepository;
import com.github.scheduler.task.TaskInstance;

import java.time.Instant;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 6:37 PM
 */
public interface SchedulerClient {
    <T> void schedule(TaskInstance<T> taskInstance, Instant executionTime);


    class StandardSchedulerClient implements SchedulerClient {

        protected final TaskRepository taskRepository;
        private SchedulerClientEventListener schedulerClientEventListener;

        StandardSchedulerClient(TaskRepository taskRepository) {
            this(taskRepository, SchedulerClientEventListener.NOOP);
        }

        StandardSchedulerClient(TaskRepository taskRepository, SchedulerClientEventListener schedulerClientEventListener) {
            this.taskRepository = taskRepository;
            this.schedulerClientEventListener = schedulerClientEventListener;
        }

        @Override
        public <T> void schedule(TaskInstance<T> taskInstance, Instant executionTime) {

        }
    }
}
