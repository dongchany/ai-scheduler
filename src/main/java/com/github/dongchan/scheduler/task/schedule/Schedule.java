package com.github.dongchan.scheduler.task.schedule;

import com.github.dongchan.scheduler.task.ExecutionComplete;

import java.time.Instant;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 4:26 PM
 */
public interface Schedule {

    Instant getNextExecutionTime(ExecutionComplete executionComplete);
    /**
     * Used to get the first execution-time for a schedule. Simulates an ExecutionComplete event.
     */
    default Instant getInitialExecutionTime(Instant now) {
        return getNextExecutionTime(ExecutionComplete.simulatedSuccess(now));
    }

}
