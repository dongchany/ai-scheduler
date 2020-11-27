package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.concurrent.LoggingRunnable;
import com.github.dongchan.scheduler.dao.TaskRepository;
import com.github.dongchan.scheduler.stats.StatsRegistry;
import com.github.dongchan.scheduler.task.*;
import com.github.scheduler.task.*;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:12 PM
 */
@Slf4j
public class Scheduler implements SchedulerClient{

    public static final String THREAD_PREFIX = "tick-scheduler";
    protected final List<OnStartup> onStartup;
    private final Clock clock;
    private final SchedulerClient delegate;
    private final TaskRepository taskRepository;
    private final TaskResolver taskResolver;
    private final int pollingLimit;
    private final ExecutorService executorService;
    private final ExecutorService dueExecutor;
    private final ExecutorService detectDeadExecutor;
    private final ExecutorService updateHeartbeatExecutor;
    private final Waiter heartbeatWaiter;
    private final Waiter executeDueWaiter;
    private final Waiter detectDeadWaiter;
    private final StatsRegistry statsRegistry;
    private final SchedulerState.SettableSchedulerState schedulerState = new SchedulerState.SettableSchedulerState();
    private int threadPoolSize;
    private int currentGenerationNumber = 1;

    protected Scheduler(Clock clock, TaskRepository taskRepository, TaskResolver taskResolver, int threadpoolSize, ExecutorService executorService, SchedulerName schedulerName,
                        Waiter executeDueWaiter, Duration heartbeatInterval, boolean enableImmediateExecution, StatsRegistry statsRegistry, int pollingLimit, Duration deleteUnresolvedAfter, List<OnStartup> onStartup) {
        this.clock = clock;
        this.taskRepository = taskRepository;
        this.executorService = executorService;
        this.executeDueWaiter = executeDueWaiter;
        this.taskResolver = taskResolver;
        this.onStartup = onStartup;
        this.detectDeadWaiter = new Waiter(heartbeatInterval.multipliedBy(2), clock);
        this.heartbeatWaiter = new Waiter(heartbeatInterval, clock);
        this.statsRegistry = statsRegistry;
        this.pollingLimit = pollingLimit;
        this.dueExecutor = Executors.newSingleThreadExecutor(ExecutorUtils.defaultThreadFactoryWithPrefix(THREAD_PREFIX + "-execute-due-"));
        this.detectDeadExecutor = Executors.newSingleThreadExecutor(ExecutorUtils.defaultThreadFactoryWithPrefix(THREAD_PREFIX + "-detect-dead-"));
        this.updateHeartbeatExecutor = Executors.newSingleThreadExecutor(ExecutorUtils.defaultThreadFactoryWithPrefix(THREAD_PREFIX + "-update-heartbeat-"));
        SchedulerClientEventListener earlyExecutionListener = (enableImmediateExecution ? new TriggerCheckForDueExecutions(schedulerState, clock, executeDueWaiter) : SchedulerClientEventListener.NOOP);
        delegate = new StandardSchedulerClient(taskRepository, earlyExecutionListener);
    }


    public static SchedulerBuilder create(DataSource dataSource, Task<?>... knownTasks) {
        return create(dataSource, Arrays.asList(knownTasks));
    }

    public static SchedulerBuilder create(DataSource dataSource, List<Task<?>> knowTasks) {
        return new SchedulerBuilder(dataSource, knowTasks);
    }

    public void start() {
        log.info("Starting scheduler.");

        executeOnStartup();
        dueExecutor.submit(new RunUntilShutdown(this::executeDue, executeDueWaiter, schedulerState, statsRegistry));
        detectDeadExecutor.submit(new RunUntilShutdown(this::detectDeadExecutions, detectDeadWaiter, schedulerState, statsRegistry));
        updateHeartbeatExecutor.submit(new RunUntilShutdown(this::updateHeartbeats, heartbeatWaiter, schedulerState, statsRegistry));


        schedulerState.setStarted(true);
    }

    protected void executeDue() {
        Instant now = clock.now();
        List<Execution> dueExecutions = taskRepository.getDue(now, pollingLimit);
        log.debug("Found {} taskInstance due for execution", dueExecutions.size());

        DueExecutionBatch newDueBatch = new DueExecutionBatch(this.threadPoolSize, currentGenerationNumber, dueExecutions.size(), pollingLimit == dueExecutions.size());

        for (Execution e : dueExecutions) {
            executorService.execute(new PickAndExecute(e, newDueBatch));
        }
        statsRegistry.register(StatsRegistry.SchedulerStatsEvent.RAN_EXECUTE_DUE);
    }

    protected void detectDeadExecutions() {

    }

    protected void updateHeartbeats() {

    }

    protected void executeOnStartup() {
        onStartup.forEach(os -> {
            os.onStartup(this, this.clock);
        });
    }
    @Override
    public <T> void schedule(TaskInstance<T> taskInstance, Instant executionTime) {
        this.delegate.schedule(taskInstance, executionTime);
    }


    private class PickAndExecute extends LoggingRunnable {
        private Execution candidate;
        private DueExecutionBatch addedDueExecutionBatch;

        public PickAndExecute(Execution candidate, DueExecutionBatch dueExecutionBatch) {
            this.candidate = candidate;
            this.addedDueExecutionBatch = dueExecutionBatch;
        }

        @Override
        public void runButLogExceptions() {
            if (schedulerState.isShuttingDown()) {
                log.info("Scheduler has been shutdown. Skipping fetched due execution: " + candidate.taskInstance.getTaskAndInstance());
                return;
            }

            try {

                final Optional<Execution> pickedExecution = taskRepository.pick(candidate, clock.now());

                try {
                    executePickedExecution(pickedExecution.get());
                }finally {

                }
            } finally {

            }
        }

        private void executePickedExecution(Execution execution) {
            final Optional<Task> task = taskResolver.resolve(execution.taskInstance.getTaskName());
            if (!task.isPresent()) {
                log.error("Failed to find implementation for task with name '{}'. Should have been excluded in JdbcRepository.", execution.taskInstance.getTaskName());
                statsRegistry.register(StatsRegistry.SchedulerStatsEvent.UNEXPECTED_ERROR);
                return;
            }

            try {
                log.debug("Executing " + execution);
                CompletionHandler completion = task.get().execute(execution.taskInstance, new ExecutionContext(schedulerState, execution, Scheduler.this));
                log.debug("Execution done");


            }catch (RuntimeException unhandlerException){

            }
        }
    }
}
