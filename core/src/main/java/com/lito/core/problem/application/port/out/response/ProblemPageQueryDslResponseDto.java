package com.lito.core.problem.application.port.out.response;

import com.querydsl.core.annotations.QueryProjection;
import com.lito.core.problem.domain.enums.ProblemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemPageQueryDslResponseDto {

    private Long problemId;

    private String subjectName;

    private String question;

    private ProblemStatus problemStatus;

    private boolean favorite;

    @QueryProjection
    public ProblemPageQueryDslResponseDto(Long problemId, String subjectName, String question, ProblemStatus problemStatus) {
        this.problemId = problemId;
        this.subjectName = subjectName;
        this.question = question;
        this.problemStatus = problemStatus == null ? ProblemStatus.NOT_SEEN : problemStatus;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

