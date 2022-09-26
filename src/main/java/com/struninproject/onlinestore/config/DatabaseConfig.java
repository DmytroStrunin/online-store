package com.struninproject.onlinestore.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The {@code DatabaseConfig} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Configuration
public class DatabaseConfig {
    private static final URI DATABASE;
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        try {
            DATABASE = new URI(System.getenv("DATABASE_URL"));
            DB_URL = String.format("jdbc:postgresql://%s:%d%s", DATABASE.getHost(), DATABASE.getPort(), DATABASE.getPath());
            DB_USER = DATABASE.getUserInfo().split(":")[0];
            DB_PASSWORD = DATABASE.getUserInfo().split(":")[1];
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        return new HikariDataSource(config);
    }
}