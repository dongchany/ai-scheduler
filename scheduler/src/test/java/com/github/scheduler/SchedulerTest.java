package com.github.scheduler;

import com.github.scheduler.task.helper.RecurringTask;
import com.github.scheduler.task.schedule.FixedDelay;
import com.github.scheduler.testhelper.SettableClock;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 11:19 PM
 */
public class SchedulerTest {

    private SettableClock clock;

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void scheduleShouldExecuteRecurringTaskAndReschedule(){
//        RecurringTask<Void> recurringTask = TestTasks.recurring("Recurring", FixedDelay.of(Duration.ofHours(1)));
    }
}
