package com.github.dongchan.scheduler.task;

import com.github.dongchan.scheduler.SchedulerClient;
import com.github.dongchan.scheduler.SchedulerState;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:02 PM
 */
public class ExecutionContext {
    private final SchedulerState schedulerState;
    private final Execution execution;
    private final SchedulerClient schedulerClient;

    public ExecutionContext(SchedulerState schedulerState, Execution execution, SchedulerClient schedulerClient) {
        this.schedulerState = schedulerState;
        this.execution = execution;
        this.schedulerClient = schedulerClient;
    }

}
