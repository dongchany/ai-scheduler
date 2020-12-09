package com.github.dongchan.scheduler.example;

import com.github.dongchan.scheduler.Scheduler;
import com.github.dongchan.scheduler.task.helper.RecurringTask;
import com.github.dongchan.scheduler.task.helper.Tasks;
import com.github.dongchan.scheduler.task.schedule.FixedDelay;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 11:12 AM
 */
public class SchedulerMain {
    private static final Logger log = LoggerFactory.getLogger(SchedulerMain.class);

    private static void example(DataSource dataSource){
        //Recurring with no data
        RecurringTask<Void> recurring1 = Tasks.recurring("recurring", FixedDelay.of(Duration.ofSeconds(3)))
                .onFailureReschedule()    // default
                .onDeadExecutionRevive()
                .execute((taskInstance, executionContext) -> {
                    sleep(100);
                    log.info("Executing "+ taskInstance.getTaskAndInstance());
                });
        // recurring with contant data
        RecurringTask<Integer> recurring2 = Tasks.recurring("recurring_constant_data", FixedDelay.of(Duration.ofSeconds(7)), Integer.class)
                .initialData(1)
                .onFailureReschedule()   // default
                .onDeadExecutionRevive() // default
                .execute((taskInstance, executionContext) -> {
                    sleep(100);
                    log.info("Executing " + taskInstance.getTaskAndInstance() + " , data: " + taskInstance.getData());
                });

        final Scheduler scheduler = Scheduler
                .create(dataSource)
                .startTasks(recurring1)
                .build();

        scheduler.start();
    }
    public static void main(String[] args){
        testDataSource("mysql");
        example(getMySQLDataSource());
    }

    private static DataSource getMySQLDataSource(){
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        String resourceName = "db.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream is =  loader.getResourceAsStream(resourceName);
            props.load(is);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
    private static void testDataSource(String dbType) {
        DataSource ds = null;
        if ("mysql".equals(dbType)) {
            ds = getMySQLDataSource();
        } else {
            System.out.println("invalid db type");
            return;
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

}
