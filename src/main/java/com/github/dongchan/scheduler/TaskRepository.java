package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.task.Execution;

/**
 * @author Dongchan Year
 */
public interface TaskRepository {

    boolean createIfNotExists(Execution execution);
}
