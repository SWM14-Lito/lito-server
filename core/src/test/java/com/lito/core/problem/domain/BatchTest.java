package com.lito.core.problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("Batch")
class BatchTest {

    @Nested
    @DisplayName("from 메서드는")
    class from{

        @Nested
        @DisplayName("taskId, date를 가지고")
        class with_taskId_date{

            String taskId = "48d0ed44-06a5-413f-ab2c-e041bef30d2b";
            LocalDate date = LocalDate.now();

            @Test
            @DisplayName("batch를 생성한다.")
            void it_creates_batch() {

                Batch batch = Batch.from(taskId, date);

                assertThat(batch.getTaskId()).isEqualTo(taskId);
            }
        }
    }
}