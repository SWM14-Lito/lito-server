package com.swm.lito.batch.job;

import com.swm.lito.batch.dto.request.ProblemUserRequest;
import com.swm.lito.batch.dto.request.ProblemUserRequestDto;
import com.swm.lito.batch.dto.response.ProblemUserResponse;
import com.swm.lito.core.problem.adapter.out.persistence.BatchRepository;
import com.swm.lito.core.problem.adapter.out.persistence.ProblemRepository;
import com.swm.lito.core.problem.adapter.out.persistence.ProblemUserRepository;
import com.swm.lito.core.problem.domain.Batch;
import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.problem.domain.ProblemUser;
import com.swm.lito.core.user.adapter.out.persistence.UserRepository;
import com.swm.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ProblemUserJobConfig {

    private final ProblemUserRepository problemUserRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final BatchRepository batchRepository;

    @Value("${ml-server.url}")
    private String ML_SERVER_URL;

    @Bean
    public Job problemUserJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("problemUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(problemUserStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step problemUserStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("problemUserStep", jobRepository)
                .tasklet(problemUserTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet problemUserTasklet(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                List<ProblemUser> problemUsers = problemUserRepository.findAll();

                List<ProblemUserRequestDto> requestDtos = problemUsers.stream()
                        .map(ProblemUserRequestDto::from)
                        .collect(Collectors.toList());

                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
                headers.setContentType(mediaType);

                Long maxUserId = userRepository.findAll()
                        .stream()
                        .mapToLong(User::getId)
                        .max()
                        .orElse(0);

                Long maxProblemId = problemRepository.findAll()
                        .stream()
                        .mapToLong(Problem::getId)
                        .max()
                        .orElse(0);

                ProblemUserRequest request = ProblemUserRequest.of(maxUserId, maxProblemId, requestDtos);
                HttpEntity<ProblemUserRequest> entity = new HttpEntity<>(request, headers);
                ProblemUserResponse response = restTemplate.postForObject(ML_SERVER_URL+"/api/v1/problems/problem-user",
                        entity, ProblemUserResponse.class);
                batchRepository.save(Batch.from(response.getTaskId(), LocalDate.now()));
                return RepeatStatus.FINISHED;
            }
        };
    }
}
