package com.github.dongchan.scheduler.task.boot.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import javax.sql.DataSource;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 10:44 PM
 */
public class DbSchedulerAutoConfigurationTest {
    private final ApplicationContextRunner ctxRunner;

    public DbSchedulerAutoConfigurationTest(){
        ctxRunner = new ApplicationContextRunner()
                .withPropertyValues(
                        "spring.application.name=db-scheduler-boot-stater.test",
                        "spring-profiles.active=integration-test"
                ).withConfiguration(AutoConfigurations.of(
                        DataSourceAutoConfiguration.class,
                        DbSchedulerAutoConfiguration.class
                ));
    }

    @Test
    public void itShouldInitializeAnEmptyScheduler(){
        ctxRunner.run((AssertableApplicationContext ctx) -> {
            assertThat(ctx).hasSingleBean(DataSource.class);
        });
    }
}
