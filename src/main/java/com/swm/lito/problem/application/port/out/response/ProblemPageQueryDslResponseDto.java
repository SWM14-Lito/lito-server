package com.swm.lito.problem.application.port.out.response;

import com.querydsl.core.annotations.QueryProjection;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import lombok.*;

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
    public ProblemPageQueryDslResponseDto(Long problemId, String subjectName, String question) {
        this.problemId = problemId;
        this.subjectName = subjectName;
        this.question = question;
    }

    public void setProblemStatus(ProblemStatus problemStatus) {
        if(problemStatus == null){
            this.problemStatus = ProblemStatus.NOT_SEEN;
            return;
        }
        this.problemStatus = problemStatus;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

