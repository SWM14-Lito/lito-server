package com.swm.lito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LitoApplication.class, args);
    }

}
