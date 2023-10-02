package com.lito.api.problem.adapter.in.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProblemPageWithFavoriteResponse {

    @Builder.Default
    private List<ProblemPageWithFavorite> problems = new ArrayList<>();

    private long total;

    public static ProblemPageWithFavoriteResponse of(List<ProblemPageWithFavorite> problems, long total){
        return ProblemPageWithFavoriteResponse.builder()
                .problems(problems)
                .total(total)
                .build();
    }
}
