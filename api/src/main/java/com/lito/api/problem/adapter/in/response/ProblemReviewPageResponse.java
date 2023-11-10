package com.lito.api.problem.adapter.in.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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
