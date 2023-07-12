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
public class ProblemPageResponse {

    @Builder.Default
    private List<ProblemPage> problems = new ArrayList<>();

    public static ProblemPageResponse from(List<ProblemPage> problems){
        return ProblemPageResponse.builder()
                .problems(problems)
                .build();
    }

}
