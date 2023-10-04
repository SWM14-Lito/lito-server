package com.lito.core.problem.application.service;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.problem.adapter.out.persistence.BatchRepository;
import com.lito.core.problem.application.port.out.BatchQueryPort;
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

import static com.lito.core.common.exception.batch.BatchErrorCode.BATCH_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("BatchQueryService")
class BatchQueryServiceTest {

    @Autowired
    BatchQueryService batchQueryService;

    @Autowired
    BatchQueryPort batchQueryPort;

    @Autowired
    BatchRepository batchRepository;

    @Nested
    @DisplayName("findBatch 메서드는")
    class find_batch{

        @Nested
        @DisplayName("requestDate를 가지고")
        class with_request_date{

            String taskId = UUID.randomUUID().toString();
            LocalDate requestDate = LocalDate.now();
            @BeforeEach
            void setUp(){
                batchRepository.save(Batch.from(taskId,requestDate));
            }
            @Test
            @DisplayName("batch를 리턴한다.")
            void with_batch() throws Exception{

                Batch batch = batchQueryService.findBatch(requestDate);

                assertThat(batch.getTaskId()).isEqualTo(taskId);
            }
        }

        @Nested
        @DisplayName("만약 batch가 존재하지 않는다면")
        class with_batch_not_found{

            @Test
            @DisplayName("BATCH_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_batch_not_found() throws Exception{

                assertThatThrownBy(() -> batchQueryService.findBatch(LocalDate.of(1997,2,3)))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(BATCH_NOT_FOUND.getMessage());

            }
        }
    }
}