package com.lito.api.problem.adapter.in.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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
