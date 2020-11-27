package com.github.dongchan.scheduler.task;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:00 PM
 */
public interface VoidExecutionHandler<T> {
    void execute(TaskInstance<T> taskInstance, ExecutionContext executionContext);
}
