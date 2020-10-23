package com.github.scheduler.task;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:40 PM
 */
@Getter
public class ExecutionComplete {
    private final Execution execution;
    private final Instant timeStarted;
    private final Instant timeDone;
    private final Result result;
    private final Throwable cause;

    ExecutionComplete(Execution execution, Instant timeStarted, Instant timeDone, Result result, Throwable cause){
        this.timeStarted = timeStarted;
        this.cause = cause;
        if (result == Result.OK && cause != null){
            throw new IllegalArgumentException("Result 'OK' should never have a cause.");
        }
        this.execution = execution;
        this.timeDone = timeDone;
        this.result = result;
    }

    public enum Result{
        OK,
        FAILED
    }


    /**
     * Simulated ExecutionComplete used to generate first execution-time from a Schedule.
     */
    public static ExecutionComplete simulatedSuccess(Instant timeDone) {
        TaskInstance nonExistingTaskInstance = new TaskInstance("non-existing-task", "non-existing-id");
        Execution nonExistingExecution = new Execution(timeDone, nonExistingTaskInstance, false, "simulated-picked-by", timeDone, null, 0, null, 1);
        return new ExecutionComplete(nonExistingExecution, timeDone.minus(Duration.ofSeconds(1)), timeDone, Result.OK, null);
    }


    public Optional<Throwable> getCause(){
        return Optional.ofNullable(cause);
    }
}
