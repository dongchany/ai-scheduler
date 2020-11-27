package com.github.dongchan.scheduler.task.helper;

import com.github.dongchan.scheduler.task.ExecutionContext;
import com.github.dongchan.scheduler.task.TaskInstance;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:41 PM
 */
public abstract class OneTimeTask<T> {

    public OneTimeTask(String name, Class<T> dataClass){
    }

    public abstract void executeOnce(TaskInstance<T> taskInstance, ExecutionContext executionContext) throws InterruptedException;
}
