package com.lito.core.problem.application.port.in.response;

import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.enums.ProblemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendUserResponseDto {

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    private Boolean favorite;

    public static RecommendUserResponseDto of(Problem problem, ProblemStatus problemStatus,  boolean favorite){
        return RecommendUserResponseDto.builder()
                .problemId(problem.getId())
                .subjectName(problem.getSubject().getSubjectName())
                .question(problem.getQuestion())
                .problemStatus(problemStatus.getName())
                .favorite(favorite)
                .build();
    }
}
