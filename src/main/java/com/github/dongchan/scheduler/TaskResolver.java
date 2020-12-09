package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.stats.StatsRegistry;
import com.github.dongchan.scheduler.task.Task;
import com.github.dongchan.scheduler.task.schedule.SystemClock;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 2:10 PM
 */
@Slf4j
public class TaskResolver {

    private final StatsRegistry statsRegistry;
    private final Clock clock;
    private final Map<String, Task> taskMap;
    private final Map<String, UnresolvedTask> unresolvedTasks = new ConcurrentHashMap<>();
    public TaskResolver(StatsRegistry statsRegistry, Task<?>... knownTasks) {
        this(statsRegistry, Arrays.asList(knownTasks));
    }

    public TaskResolver(StatsRegistry statsRegistry, List<Task<?>> knownTasks) {
        this(statsRegistry, new SystemClock(), knownTasks);
    }

    public TaskResolver(StatsRegistry statsRegistry, Clock clock, List<Task<?>> knownTasks) {
        this.statsRegistry = statsRegistry;
        this.clock = clock;
        this.taskMap = knownTasks.stream().collect(Collectors.toMap(Task::getName, identity()));
    }
    public Optional<Task> resolve(String taskName) {
        Task task = taskMap.get(taskName);
        if (task == null) {
            addUnresolved(taskName);
            statsRegistry.register(StatsRegistry.SchedulerStatsEvent.UNRESOLVED_TASK);
            log.info("Found execution with unknown task-name '{}'. Adding it to the list of known unresolved task-names.", taskName);
        }
        return Optional.ofNullable(task);
    }

    private void addUnresolved(String taskName) {
        unresolvedTasks.putIfAbsent(taskName, new UnresolvedTask(taskName));
    }

    public List<UnresolvedTask> getUnresolved(){
        return new ArrayList<>(unresolvedTasks.values());
    }
    public class UnresolvedTask{
        private final String taskName;
        private final Instant firstUnresolved;

        public UnresolvedTask(String taskName) {
            this.taskName = taskName;
            this.firstUnresolved = clock.now();
        }

        public String getTaskName() {
            return taskName;
        }
    }
}
