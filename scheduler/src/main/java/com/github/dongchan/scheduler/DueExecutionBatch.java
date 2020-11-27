package com.github.dongchan.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 4:03 PM
 */
public class DueExecutionBatch {
    private int threadPoolSize;
    private final int generationNumber;
    private final AtomicInteger executionsLeftInBatch;
    private boolean possiblyMoreExecutionsInDb;

    public DueExecutionBatch(int threadPoolSize, int generationNumber, int executionsAdded, boolean possiblyMoreExecutionsInDb) {
        this.threadPoolSize = threadPoolSize;
        this.generationNumber = generationNumber;
        this.possiblyMoreExecutionsInDb = possiblyMoreExecutionsInDb;
        this.executionsLeftInBatch = new AtomicInteger(executionsAdded);
    }

}
