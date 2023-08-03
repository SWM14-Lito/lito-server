package com.swm.lito.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
        "com.swm.lito.core.company",
        "com.swm.lito.core.problem",
        "com.swm.lito.core.user",
})
public class JpaConfig {
}
