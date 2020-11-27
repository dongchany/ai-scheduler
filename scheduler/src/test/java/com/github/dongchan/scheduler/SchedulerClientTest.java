package com.github.dongchan.scheduler;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class SchedulerClientTest {

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void client_should_be_able_to_schedule_executions(){
        SchedulerClient client = SchedulerClient.Builder.create(DB.getDataSource()).build;
    }
}
