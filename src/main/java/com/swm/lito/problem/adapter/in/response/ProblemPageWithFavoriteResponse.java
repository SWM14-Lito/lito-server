package com.swm.lito.problem.adapter.in.response;

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
public class ProblemPageWithFavoriteResponse {

    @Builder.Default
    private List<ProblemPageWithFavorite> problems = new ArrayList<>();

    public static ProblemPageWithFavoriteResponse from(List<ProblemPageWithFavorite> problems){
        return ProblemPageWithFavoriteResponse.builder()
                .problems(problems)
                .build();
    }
}
