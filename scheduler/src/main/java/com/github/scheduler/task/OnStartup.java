package com.github.scheduler.task;

import com.github.scheduler.Clock;
import com.github.scheduler.Scheduler;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 1:43 PM
 */
public interface OnStartup {
    void onStartup(Scheduler scheduler, Clock clock);
}
