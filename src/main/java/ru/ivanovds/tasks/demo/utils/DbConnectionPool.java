package ru.ivanovds.tasks.demo.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbConnectionPool {
    private static HikariDataSource ds;

    public static DataSource getConnection() {
        if (ds == null) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("org.postgresql.Driver");
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            config.setUsername("postgres");
            config.setPassword("admin");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            ds = new HikariDataSource(config);
        }
        return ds;
    }

    @Bean
    public DSLContext context() {
        return DSL.using(getConnection(), SQLDialect.POSTGRES);
    }
}
