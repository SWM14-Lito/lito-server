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
public class ProblemPageWithProcessResponse {

    @Builder.Default
    private List<ProblemPageWithProcess> problems = new ArrayList<>();

    public static ProblemPageWithProcessResponse from(List<ProblemPageWithProcess> problems){
        return ProblemPageWithProcessResponse.builder()
                .problems(problems)
                .build();
    }
}
