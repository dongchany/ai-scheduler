package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.jdbc.AutodetectJdbcCustomization;
import com.github.dongchan.scheduler.task.Execution;
import com.github.dongchan.scheduler.task.TaskInstance;
import com.github.dongchan.scheduler.task.helper.OneTimeTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.Instant;

import static com.github.dongchan.scheduler.JdbcTaskRepository.DEFAULT_TABLE_NAME;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.jupiter.api.Assertions.*;

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
    public void setUp(){
        oneTimeTask = TestTasks.oneTime("OneTime", Void.class, TestTasks.DO_NOTHING);
        taskRepository = new JdbcTaskRepository(extension.getDataSource(),new AutodetectJdbcCustomization(extension.getDataSource()), DEFAULT_TABLE_NAME,Serializer.DEFAULT_JAVA_SERIALIZER);
    }

    @Test
    public void testCreateIfNotExists(){
        Instant now = truncatedInstantNow();

        TaskInstance<Void> instance1 = oneTimeTask.instance("id1");
        TaskInstance<Void> instance2 = oneTimeTask.instance("id2");

        assertTrue(taskRepository.createIfNotExists(new Execution(now, instance1)));
        assertFalse(taskRepository.createIfNotExists(new Execution(now, instance1)));

        assertTrue(taskRepository.createIfNotExists(new Execution(now, instance2)));
    }

    private Instant truncatedInstantNow(){
        return Instant.now().truncatedTo(MILLIS);
    }
}
