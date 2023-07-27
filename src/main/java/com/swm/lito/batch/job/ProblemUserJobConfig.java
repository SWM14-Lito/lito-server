package com.swm.lito.batch.job;

import com.swm.lito.batch.dto.response.ProblemUserResponse;
import com.swm.lito.problem.adapter.out.persistence.ProblemUserRepository;
import com.swm.lito.problem.domain.ProblemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProblemUserJobConfig {

    private final ProblemUserRepository problemUserRepository;

    private final int CHUNK_PAGE_SIZE = 1000;

    @Bean
    public Job problemUserJob(JobRepository jobRepository, Step step){
        return new JobBuilder("problemUserJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    @JobScope
    public Step problemUserStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("problemUserStep", jobRepository)
                .<ProblemUser, ProblemUserResponse>chunk(CHUNK_PAGE_SIZE, transactionManager)
                .reader(problemUserReader())
                .processor(problemUserProcessor())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<ProblemUser> problemUserReader(){
        return new RepositoryItemReaderBuilder<ProblemUser>()
                .name("problemUserReader")
                .repository(problemUserRepository)
                .methodName("findAll")
                .pageSize(CHUNK_PAGE_SIZE)
                .arguments(List.of())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<ProblemUser, ProblemUserResponse> problemUserProcessor(){
        return ProblemUserResponse::from;
    }
}
