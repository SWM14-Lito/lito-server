package com.swm.lito.core.problem.application.port.out.response;

import com.querydsl.core.annotations.QueryProjection;
import com.swm.lito.core.problem.domain.enums.ProblemStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ProblemPageWithFavoriteQResponseDto {

    private Long favoriteId;

    private Long problemId;

    private String subjectName;

    private String question;

    private ProblemStatus problemStatus;

    @QueryProjection
    public ProblemPageWithFavoriteQResponseDto(Long favoriteId, Long problemId, String subjectName, String question, ProblemStatus problemStatus) {
        this.favoriteId = favoriteId;
        this.problemId = problemId;
        this.subjectName = subjectName;
        this.question = question;
        this.problemStatus = problemStatus == null ? ProblemStatus.NOT_SEEN : problemStatus;
    }

}
