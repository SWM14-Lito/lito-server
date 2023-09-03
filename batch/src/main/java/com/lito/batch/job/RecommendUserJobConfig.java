package com.lito.batch.job;

import com.lito.batch.dto.response.RecommendUserResponse;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.batch.BatchErrorCode;
import com.lito.core.problem.adapter.out.persistence.BatchRepository;
import com.lito.core.problem.adapter.out.persistence.RecommendUserJdbcRepositoryImpl;
import com.lito.core.problem.adapter.out.persistence.RecommendUserRepository;
import com.lito.core.problem.domain.Batch;
import com.lito.core.problem.domain.RecommendUser;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RecommendUserJobConfig {

    private final BatchRepository batchRepository;
    private final RecommendUserRepository recommendUserRepository;

    @Value("${ml-server.url}")
    private String ML_SERVER_URL;

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
                .tasklet(recommendUserTasklet(), transactionManager)
                .build();

    }

    @Bean
    public Tasklet recommendUserTasklet(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                Batch batch = batchRepository.findTopByRequestDateOrderByCreatedAtDesc(LocalDate.now())
                        .orElseThrow(() -> new ApplicationException(BatchErrorCode.BATCH_NOT_FOUND));
                RestTemplate restTemplate = new RestTemplate();
                RecommendUserResponse response = restTemplate.getForObject(ML_SERVER_URL+"/api/v1/problems/recommend-user/"+batch.getTaskId(),
                        RecommendUserResponse.class);
                List<RecommendUser> recommendUsers = response.getData().stream()
                        .map(r -> RecommendUser.createRecommendUser(r.getUserId(), r.getProblemId()))
                        .toList();
                recommendUserRepository.saveRecommendUsers(recommendUsers);
                return RepeatStatus.FINISHED;
            }
        };
    }
}