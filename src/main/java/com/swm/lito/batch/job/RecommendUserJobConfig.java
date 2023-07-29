package com.swm.lito.batch.job;

import com.swm.lito.batch.domain.Batch;
import com.swm.lito.batch.domain.BatchRepository;
import com.swm.lito.batch.dto.response.RecommendUserResponse;
import com.swm.lito.batch.dto.response.RecommendUserResponseDto;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.batch.BatchErrorCode;
import com.swm.lito.problem.adapter.out.persistence.RecommendUserRepository;
import com.swm.lito.problem.domain.RecommendUser;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RecommendUserJobConfig {

    private final BatchRepository batchRepository;
    private final RecommendUserRepository recommendUserRepository;

    @Value("${ml-server.url}")
    private String ML_SERVER_URL;

    private final int CHUNK_SIZE = 1000;

    @Bean
    public Job recommendUserJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("recommendUserJob", jobRepository)
                .start(recommendUserStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step recommendUserStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("recommendUserStep", jobRepository)
                .<RecommendUserResponseDto, RecommendUserResponseDto>chunk(CHUNK_SIZE, transactionManager)
                .reader(recommendUserReader())
                .writer(recommendUserWriter())
                .build();

    }

    @Bean
    @StepScope
    public ItemReader<RecommendUserResponseDto> recommendUserReader() {
        Batch batch = batchRepository.findByRequestDate(LocalDate.now())
                .orElseThrow(() -> new ApplicationException(BatchErrorCode.BATCH_NOT_FOUND));
        RestTemplate restTemplate = new RestTemplate();
        RecommendUserResponse response = restTemplate.getForObject(ML_SERVER_URL+"/api/v1/problems/recommend-user/"+batch.getTargetId(),
                RecommendUserResponse.class);
        return new ListItemReader<>(response.getData());
    }

    @Bean
    @StepScope
    public ItemWriter<RecommendUserResponseDto> recommendUserWriter() {
        return new ItemWriter<RecommendUserResponseDto>() {
            @Override
            public void write(Chunk<? extends RecommendUserResponseDto> chunk) throws Exception {
                List<RecommendUserResponseDto> responseDtos = new ArrayList<>();
                chunk.forEach(responseDtos::add);
                List<RecommendUser> recommendUsers = responseDtos.stream()
                        .map(r -> RecommendUser.createRecommendUser(r.getUserId(), r.getProblemId()))
                        .toList();
                recommendUserRepository.saveAll(recommendUsers);
            }
        };

    }
}
