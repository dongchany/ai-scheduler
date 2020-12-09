package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.task.TaskInstance;
import com.github.dongchan.scheduler.dao.TaskRepository;
import com.github.dongchan.scheduler.task.Execution;

import javax.sql.DataSource;
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
            boolean success = taskRepository.createIfNotExists(new Execution(executionTime, taskInstance));
            if (success) {
                notifyListeners();
            }
        }

        private void notifyListeners(){

        }
    }

    class Builder {

        private final DataSource dataSource;

        private Builder(DataSource dataSource){
            this.dataSource = dataSource;
        }

        public static Builder create(DataSource dataSource){
            return new Builder(dataSource);
        }

        public SchedulerClient build(){
            return null;
        }
    }
}
