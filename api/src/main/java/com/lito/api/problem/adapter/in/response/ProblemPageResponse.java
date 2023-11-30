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
public class ProblemPageResponse {

    @Builder.Default
    private List<ProblemPage> problems = new ArrayList<>();

    private long total;

    public static ProblemPageResponse of(List<ProblemPage> problems, long total){
        return ProblemPageResponse.builder()
                .problems(problems)
                .total(total)
                .build();
    }

}
