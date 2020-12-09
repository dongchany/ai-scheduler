package com.github.dongchan.scheduler;

import com.github.dongchan.jdbc.JdbcRunner;
import com.github.dongchan.scheduler.jdbc.AutodetectJdbcCustomization;
import com.github.dongchan.scheduler.task.Execution;
import com.github.dongchan.scheduler.task.TaskInstance;
import com.github.dongchan.scheduler.task.helper.OneTimeTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.Instant;
import java.util.Arrays;

import static com.github.dongchan.scheduler.JdbcTaskRepository.DEFAULT_TABLE_NAME;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Dongchan Year
 */
public class JdbcTaskRepositoryTest {

    public static final String SCHEDULER_NAME = "scheduler1";

    @RegisterExtension
    public EmbeddedMySQLExtension extension = new EmbeddedMySQLExtension();

    private OneTimeTask<Void> oneTimeTask;
    private JdbcTaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        oneTimeTask = TestTasks.oneTime("OneTime", Void.class, TestTasks.DO_NOTHING);
        taskRepository = new JdbcTaskRepository(extension.getDataSource(), new AutodetectJdbcCustomization(extension.getDataSource()), DEFAULT_TABLE_NAME, Serializer.DEFAULT_JAVA_SERIALIZER);
    }

    @Test
    public void testCreateIfNotExists() {
        Instant now = truncatedInstantNow();

        TaskInstance<Void> instance1 = oneTimeTask.instance("id1");
        TaskInstance<Void> instance2 = oneTimeTask.instance("id2");

        assertTrue(taskRepository.createIfNotExists(new Execution(now, instance1)));
        assertFalse(taskRepository.createIfNotExists(new Execution(now, instance1)));

        assertTrue(taskRepository.createIfNotExists(new Execution(now, instance2)));

        cleanTestingData(DEFAULT_TABLE_NAME, instance1, instance2);
    }

    @Test
    public void get_due_should_only_include_due_executions() {
        Instant now = truncatedInstantNow();

        taskRepository.createIfNotExists(new Execution(now, oneTimeTask.instance("id1")));
        assertThat(taskRepository.getDue(now, 1), hasSize(1));
    }

    private int cleanTestingData(String tableName, TaskInstance<Void>... instances) {
        JdbcRunner jdbcRunner = new JdbcRunner(extension.getDataSource());
        int cleanCount = Arrays.stream(instances).mapToInt(instance -> jdbcRunner.execute("delete from " + tableName + " where task_instance = ?",
                ps -> {
                    ps.setString(1, instance.getId());
                })).sum();

        return cleanCount;
    }

    private Instant truncatedInstantNow() {
        return Instant.now().truncatedTo(MILLIS);
    }
}
