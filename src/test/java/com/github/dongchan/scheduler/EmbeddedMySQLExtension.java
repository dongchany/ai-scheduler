package com.github.dongchan.scheduler;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.extension.Extension;

import javax.sql.DataSource;

/**
 * @author Dongchan Year
 */
public class EmbeddedMySQLExtension implements Extension {

    private final DataSource dataSource;

    public EmbeddedMySQLExtension() {
        synchronized (this){
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://192.168.43.251:33081/easesqlbot?useUnicode=true&characterEncoding=utf-8&useSSL=false&useTimezone=true&serverTimezone=GMT%2B8");
            config.setUsername("super");
            config.setPassword("super");
            this.dataSource = new HikariDataSource(config);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
