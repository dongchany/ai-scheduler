package com.github.dongchan.scheduler.task;

import lombok.Getter;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 4:11 PM
 */
@Getter
public abstract class Task<T>  {
    protected final String name;
    private final FailureHandler<T> failureHandler;
    private final DeadExecutionHandler<T> deadExecutionHandler;
    private final Class<T> dataClass;

    public Task(String name, Class<T> dataClass, FailureHandler<T> failureHandler, DeadExecutionHandler<T> deadExecutionHandler){
        this.name = name;
        this.dataClass = dataClass;
        this.failureHandler = failureHandler;
        this.deadExecutionHandler = deadExecutionHandler;
    }

    public TaskInstance<T> createInstance(String id) {
        return new TaskInstance<>(this.name, id);
    }

    public TaskInstance<T> createInstance(String id, T data){
        return new TaskInstance<>(this.name, id, data);
    }

    public abstract CompletionHandler<T> execute(TaskInstance<T> taskInstance, ExecutionContext executionContext);
}
