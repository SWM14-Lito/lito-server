package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.domain.Batch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("BatchQueryAdapter")
class BatchQueryAdapterTest {

    @Autowired
    BatchQueryAdapter batchQueryAdapter;

    @Autowired
    BatchRepository batchRepository;

    @Nested
    @DisplayName("findBatch 메서드는")
    class find_batch{

        String taskId = UUID.randomUUID().toString();
        LocalDate requestDate = LocalDate.now();
        @BeforeEach
        void setUp(){
            batchRepository.save(Batch.from(taskId,requestDate));
        }
        @Nested
        @DisplayName("requestDate를 가지고")
        class with_request_date{

            @Test
            @DisplayName("batch를 리턴한다.")
            void it_returns_batch() throws Exception{

                Batch batch = batchQueryAdapter.findBatch(requestDate).get();

                assertThat(batch.getTaskId()).isEqualTo(taskId);
            }
        }
    }
}