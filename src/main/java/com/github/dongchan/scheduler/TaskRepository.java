package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.task.Execution;

import java.time.Instant;
import java.util.List;

/**
 * @author Dongchan Year
 */
public interface TaskRepository {

    boolean createIfNotExists(Execution execution);
    List<Execution> getDue(Instant now, int limit);
    int remove(Execution execution);
}
