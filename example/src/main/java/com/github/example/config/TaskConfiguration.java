package com.github.example.config;

import com.github.example.CounterService;
import com.github.dongchan.scheduler.task.Task;
import com.github.dongchan.scheduler.task.helper.RecurringTask;
import com.github.dongchan.scheduler.task.helper.Tasks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.Duration;

import static com.github.dongchan.scheduler.task.schedule.Schedules.fixedDelay;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 3:59 PM
 */
@Configuration
@Slf4j
public class TaskConfiguration {

    @Autowired
    DataSource dataSource;
    /**
     * Define a recurring task with a dependency, which will automatically be picked up
     * by the Spring Boot autoconfiguration.
     * @param counter
     * @return
     */
    @Bean
    Task<Void> recurringSampleTask(CounterService counter) {
        log.info("Start recurring of  bean class");
        return Tasks
                .recurring("recurring-smaple-task", fixedDelay(Duration.ofMinutes(1)))
                .execute((instance, ctx) -> {
            log.info("Running recurring-simple-task. Instance: {}, ctx: {}", instance, ctx);
            counter.increase();
        });
    }

    @Bean
    void startRecurringTask(){
        RecurringTask<Void> minTask = Tasks.recurring("my-min-task", fixedDelay(Duration.ofSeconds(3)))
                .execute( (instance, ctx) -> {
                   log.info("Executed my-min-task!");
                });

//        final Scheduler scheduler = Scheduler.create(dataSource)
//                .startTask(minTask)
//                .threads(5)
//                .build();
//
//        scheduler.start();
    }
}
