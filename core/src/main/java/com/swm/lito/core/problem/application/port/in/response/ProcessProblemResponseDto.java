package com.swm.lito.core.problem.application.port.in.response;

import com.swm.lito.core.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessProblemResponseDto {

    private Long problemId;

    private String subject;

    private String question;

    private Boolean favorite;

    public static ProcessProblemResponseDto of(Problem problem, boolean favorite){
        return ProcessProblemResponseDto.builder()
                .problemId(problem.getId())
                .subject(problem.getSubject().getSubjectName())
                .question(problem.getQuestion())
                .favorite(favorite)
                .build();
    }
}
