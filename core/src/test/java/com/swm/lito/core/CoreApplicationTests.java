package com.swm.lito.core;

import com.swm.lito.core.config.TestRedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestRedisConfig.class)
public class CoreApplicationTests {

    @Test
    void contextLoads() {
    }
}
