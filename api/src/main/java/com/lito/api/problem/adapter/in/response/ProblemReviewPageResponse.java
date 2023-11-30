package com.lito.api.problem.adapter.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemReviewPageResponse {

    @Builder.Default
    private List<ProblemReviewPage> problems = new ArrayList<>();

    private long total;

    public static ProblemReviewPageResponse of(List<ProblemReviewPage> problems, long total){
        return ProblemReviewPageResponse.builder()
                .problems(problems)
                .total(total)
                .build();
    }
}
