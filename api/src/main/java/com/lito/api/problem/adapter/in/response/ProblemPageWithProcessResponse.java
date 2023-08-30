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
public class ProblemPageWithProcessResponse {

    @Builder.Default
    private List<ProblemPageWithProcess> problems = new ArrayList<>();

    private long total;

    public static ProblemPageWithProcessResponse of(List<ProblemPageWithProcess> problems, long total){
        return ProblemPageWithProcessResponse.builder()
                .problems(problems)
                .total(total)
                .build();
    }
}
