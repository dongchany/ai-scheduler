package com.github.scheduler;

import com.github.scheduler.stats.StatsRegistry;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 3:33 PM
 */
public class RunUntilShutdown implements Runnable {

    private final Runnable toRun;
    private final Waiter waitBetweenRuns;
    private final SchedulerState schedulerState;
    private final StatsRegistry statsRegistry;

    public RunUntilShutdown(Runnable toRun, Waiter waitBetweenRuns, SchedulerState schedulerState, StatsRegistry statsRegistry) {
        this.toRun = toRun;
        this.waitBetweenRuns = waitBetweenRuns;
        this.schedulerState = schedulerState;
        this.statsRegistry = statsRegistry;
    }

    @Override
    public void run() {
        while (!schedulerState.isShuttingDown()) {
            try {
                toRun.run();
            } catch (Throwable e) {

            }

            try {
                waitBetweenRuns.doWait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}
