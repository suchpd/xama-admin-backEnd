package com.xama.backend.infrastructure.security;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionFactory {
    private static final Object $LOCK = new Object[0];
    private static BasicDataSource dataSource;

    public DbConnectionFactory(){

    }

    public static Connection getConnection() throws SQLException, IOException {
        synchronized($LOCK) {
            if (dataSource == null) {
                Resource resource = new ClassPathResource("/application.yml");
                Properties props = PropertiesLoaderUtils.loadProperties(resource);
                Resource activeResource = new ClassPathResource("/application-" + props.getProperty("spring.profiles.active") + ".properties");
                Properties activeProps = PropertiesLoaderUtils.loadProperties(activeResource);
                PooledPBEStringEncryptor pooledPBEStringEncryptor = EncryptorBeanConfig.initStringEncryptor();
                dataSource = new BasicDataSource();
                dataSource.setUrl(activeProps.getProperty("spring.datasource.url"));
                dataSource.setDriverClassName(activeProps.getProperty("spring.datasource.driver-class-name"));
                String jdbcUrl = activeProps.getProperty("spring.datasource.url");
                String username;
                if (jdbcUrl.startsWith("ENC(")) {
                    username = pooledPBEStringEncryptor.decrypt(jdbcUrl.substring(4, jdbcUrl.length() - 1));
                    dataSource.setUrl(username);
                } else {
                    dataSource.setUrl(jdbcUrl);
                }

                username = activeProps.getProperty("spring.datasource.username");
                String password;
                if (username.startsWith("ENC(")) {
                    password = pooledPBEStringEncryptor.decrypt(username.substring(4, username.length() - 1));
                    dataSource.setUsername(password);
                } else {
                    dataSource.setUsername(username);
                }

                password = activeProps.getProperty("spring.datasource.password");
                if (password.startsWith("ENC(")) {
                    String encryptorString = pooledPBEStringEncryptor.decrypt(password.substring(4, password.length() - 1));
                    dataSource.setPassword(encryptorString);
                } else {
                    dataSource.setPassword(password);
                }
            }

            return dataSource.getConnection();
        }
    }
}
