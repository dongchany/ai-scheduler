package com.github.dongchan.scheduler;

import com.github.dongchan.jdbc.JdbcRunner;
import com.github.dongchan.jdbc.SQLRuntimeException;
import com.github.dongchan.scheduler.jdbc.JdbcCustomization;
import com.github.dongchan.scheduler.task.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.List;

/**
 * @author Dongchan Year
 */
public class JdbcTaskRepository implements TaskRepository {

    public static final String DEFAULT_TABLE_NAME = "scheduled_tasks";
    private static final Logger log = LoggerFactory.getLogger(JdbcTaskRepository.class);
    private final JdbcRunner jdbcRunner;
    private final JdbcCustomization jdbcCustomization;
    private final String tableName;
    private final Serializer serializer;

    public JdbcTaskRepository(DataSource dataSource, JdbcCustomization jdbcCustomization, String tableName, Serializer serializer) {
        this.jdbcRunner = new JdbcRunner(dataSource);
        this.jdbcCustomization = jdbcCustomization;
        this.tableName = tableName;
        this.serializer = serializer;
    }

    @Override
    public boolean createIfNotExists(Execution execution) {
        try {
            jdbcRunner.execute("insert into " + tableName + "(task_name, task_instance, task_data, execution_time, picked, version) values(?, ?, ?, ?, ?, ?)",
                    (PreparedStatement p) -> {
                        p.setString(1, execution.taskInstance.getTaskName());
                        p.setString(2, execution.taskInstance.getId());
                        p.setObject(3, serializer.serialize(execution.taskInstance.getData()));
                        jdbcCustomization.setInstant(p, 4, execution.executionTime);
                        p.setBoolean(5, false);
                        p.setLong(6, 1L);
                    });
        }catch (SQLRuntimeException e){
            log.debug("Exception when inserting execution. Assuming it to be a constraint violation.",e);

            return false;
        }
        return true;
    }

    @Override
    public List<Execution> getDue(Instant now, int limit) {
        return null;
    }

    @Override
    public int remove(Execution execution) {
        final int removed = jdbcRunner.execute("delete from "+tableName +" where task_name = ? and task_instance = ? and version = ?",
                ps -> {

                });

        return removed;
    }

}
