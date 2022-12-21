package com.xama.backend.api.configurations;

import com.xama.backend.infrastructure.security.EncryptorBeanConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class DBConfiguration {

    @Bean
    public DataSource dataSource() throws IOException {
        Resource resource = new ClassPathResource("/application.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        Resource activeResource = new ClassPathResource("/application-"+props.getProperty("spring.profiles.active")+".properties");
        Properties activeProps = PropertiesLoaderUtils.loadProperties(activeResource);
        PooledPBEStringEncryptor pooledPBEStringEncryptor = EncryptorBeanConfig.initStringEncryptor();
        String jdbcUrl = activeProps.getProperty("spring.datasource.url");
        String username = activeProps.getProperty("spring.datasource.username");
        String password = activeProps.getProperty("spring.datasource.password");
        String driverClassName = activeProps.getProperty("spring.datasource.driver-class-name");
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);

        if (jdbcUrl.startsWith("ENC(")) {
            jdbcUrl = pooledPBEStringEncryptor.decrypt(jdbcUrl.substring(4, jdbcUrl.length() - 1));
            hikariDataSource.setJdbcUrl(jdbcUrl);
        } else {
            hikariDataSource.setJdbcUrl(jdbcUrl);
        }

        if (username.startsWith("ENC(")) {
            username = pooledPBEStringEncryptor.decrypt(username.substring(4, username.length() - 1));
            hikariDataSource.setUsername(username);
        } else {
            hikariDataSource.setUsername(username);
        }

        if (password.startsWith("ENC(")) {
            password = pooledPBEStringEncryptor.decrypt(password.substring(4, password.length() - 1));
            hikariDataSource.setPassword(password);
        } else {
            hikariDataSource.setPassword(password);
        }

        return hikariDataSource;
    }
}
