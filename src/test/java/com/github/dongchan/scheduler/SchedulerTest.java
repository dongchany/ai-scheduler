package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.stats.StatsRegistry;
import com.github.dongchan.scheduler.task.Task;
import com.github.dongchan.scheduler.task.helper.OneTimeTask;
import com.github.dongchan.scheduler.testhelper.SettableClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.util.concurrent.MoreExecutors;


import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:19 PM
 */
public class SchedulerTest {

    private TestTasks.CountingHandler<Void> handler;
    private SettableClock clock;

    @BeforeEach
    public void setUp(){
        handler = new TestTasks.CountingHandler<>();
    }

    private Scheduler schedulerFor(Task<?>... tasks){
        return schedulerFor(MoreExecutors.newDirectExecutorService(), tasks);
    }

    private Scheduler schedulerFor(ExecutorService executor, Task<?>... tasks){
        final StatsRegistry statsRegistry = StatsRegistry.NOOP;

//        JdbcTaskRepository taskRepository = new JdbcTaskRepository()
//        return new Scheduler(clock, )
        return null;
    }

    @Test
    public void scheduler_should_execute_task_when_exactly_due(){
        OneTimeTask<Void> oneTimeTask = TestTasks.oneTime("OneTime", Void.class, handler);

        assertThat(handler.timesExecuted.get(), is(0));


        assertThat(handler.timesExecuted.get(), is(1));
    }

    @Test
    public void scheduleShouldExecuteRecurringTaskAndReschedule(){
//        RecurringTask<Void> recurringTask = TestTasks.recurring("Recurring", FixedDelay.of(Duration.ofHours(1)));
    }
}
