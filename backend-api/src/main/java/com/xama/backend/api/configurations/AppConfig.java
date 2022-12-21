package com.xama.backend.api.configurations;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.xama.backend"})
@EnableJpaRepositories("com.xama.backend.dao.repository")
@EntityScan(basePackages = "com.xama.backend.domain.entity")
public class AppConfig {
}
