package com.github.scheduler;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 7:02 PM
 */
public class TriggerCheckForDueExecutions implements SchedulerClientEventListener{
    private SchedulerState schedulerState;
    private Clock clock;
    private Waiter executeDueWaiter;

    public TriggerCheckForDueExecutions(SchedulerState schedulerState, Clock clock, Waiter executeDueWaiter) {
        this.schedulerState = schedulerState;
        this.clock = clock;
        this.executeDueWaiter = executeDueWaiter;
    }

    @Override
    public void newEvent(ClientEvent event) {

    }
}
