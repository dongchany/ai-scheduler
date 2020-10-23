package com.github.example;

import com.github.scheduler.task.Task;
import com.github.scheduler.task.helper.RecurringTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 6:26 PM
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SmokeTest {
    @Autowired
    ConfigurableWebApplicationContext applicationContext;
    AssertableWebApplicationContext ctx;

    @Before
    public void setUp(){
        this.ctx = AssertableWebApplicationContext.get(() -> applicationContext);
    }

    @Test
    public void itShouldLoadContext(){
        assertThat(ctx).hasNotFailed();
    }

    @Test
    public void itShouldHaveTwoTasksExposedAsBeans(){
        assertThat(ctx.getBeansOfType(Task.class).values()).hasSize(2);
        assertThat(ctx.getBeansOfType(RecurringTask.class).values()).hasSize(1);
    }
}
