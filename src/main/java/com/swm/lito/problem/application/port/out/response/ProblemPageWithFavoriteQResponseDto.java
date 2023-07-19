package com.swm.lito.problem.application.port.out.response;

import com.querydsl.core.annotations.QueryProjection;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemPageWithFavoriteQResponseDto {

    private Long favoriteId;

    private Long problemId;

    private String subjectName;

    private String question;

    private ProblemStatus problemStatus;

    @QueryProjection
    public ProblemPageWithFavoriteQResponseDto(Long favoriteId, Long problemId, String subjectName, String question) {
        this.favoriteId = favoriteId;
        this.problemId = problemId;
        this.subjectName = subjectName;
        this.question = question;
    }

    public void setProblemStatus(ProblemStatus problemStatus) {
        this.problemStatus = problemStatus;
    }
}
