package com.lito.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
        "com.lito.core.company",
        "com.lito.core.problem",
        "com.lito.core.user",
        "com.lito.core.admin"
})
public class JpaConfig {
}
