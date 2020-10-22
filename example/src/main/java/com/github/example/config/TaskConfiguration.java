package com.github.example.config;

import com.github.example.CounterService;
import com.github.scheduler.task.Task;
import com.github.scheduler.task.helper.Tasks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static com.github.scheduler.task.schedule.Schedules.fixedDelay;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 3:59 PM
 */
@Configuration
@Slf4j
public class TaskConfiguration {

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
}
