package com.xama.backend.infrastructure.security;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EncryptorBeanConfig {
    private static PooledPBEStringEncryptor encryptor;

    public EncryptorBeanConfig() {
    }

    @Bean(
            name = {"encryptorBean"}
    )
    public StringEncryptor stringEncryptor() {
        return initStringEncryptor();
    }

    public static PooledPBEStringEncryptor initStringEncryptor() {
        if (encryptor == null) {
            encryptor = new PooledPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword("A4CAF192-84FF-4B9E-80D1-E96A58F26F09");
            config.setAlgorithm("PBEWITHMD5ANDDES");
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setProviderName("SunJCE");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setStringOutputType("base64");
            encryptor.setConfig(config);
        }

        return encryptor;
    }
}
