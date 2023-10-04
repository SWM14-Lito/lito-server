package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.domain.Batch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("BatchCommandAdapter")
class BatchCommandAdapterTest {

    @Autowired
    BatchCommandAdapter batchCommandAdapter;

    @Autowired
    BatchRepository batchRepository;

    @Nested
    @DisplayName("save 메서드는")
    class save{

        @Nested
        @DisplayName("batch를 가지고")
        class with_batch{

            String taskId = UUID.randomUUID().toString();
            LocalDate requestDate = LocalDate.now();
            Batch batch = Batch.from(taskId, requestDate);

            @Test
            @DisplayName("db에 저장한다.")
            void it_saves_batch() throws Exception{

                assertThatCode(() -> batchCommandAdapter.save(batch))
                        .doesNotThrowAnyException();
            }
        }
    }

}