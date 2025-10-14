package com.lxp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig implements AutoCloseable {

    private final HikariDataSource dataSource;

    public DBConfig() {
        this.dataSource = getDataSource(new Properties());

        if (dataSource == null) {
            throw new NullPointerException("dataSource is null");
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private HikariDataSource getDataSource(Properties props) {
        try (InputStream is = DBConfig.class.getClassLoader().getResourceAsStream("env.properties")) {
            props.load(is);

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(3000);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(2000);

            return new HikariDataSource(config);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
