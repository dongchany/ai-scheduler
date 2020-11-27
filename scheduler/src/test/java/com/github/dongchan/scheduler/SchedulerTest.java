package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.testhelper.SettableClock;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

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
