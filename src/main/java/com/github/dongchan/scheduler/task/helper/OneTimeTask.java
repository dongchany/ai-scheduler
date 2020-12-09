package com.github.dongchan.scheduler.task.helper;

import com.github.dongchan.scheduler.task.*;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:41 PM
 */
public abstract class OneTimeTask<T> extends Task<T> {

    public OneTimeTask(String name, Class<T> dataClass) {
        this(name, dataClass, null, null);
    }

    public OneTimeTask(String name, Class<T> dataClass, FailureHandler<T> failureHandler, DeadExecutionHandler<T> deadExecutionHandler) {
        super(name, dataClass, failureHandler, deadExecutionHandler);
    }

    @Override
    public CompletionHandler<T> execute(TaskInstance<T> taskInstance, ExecutionContext executionContext) {
        return null;
    }

    public abstract void executeOnce(TaskInstance<T> taskInstance, ExecutionContext executionContext) throws InterruptedException;
}
