package com.swm.lito.problem.adapter.in.response;

import com.swm.lito.problem.application.port.in.response.ProblemPageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemPageResponse {

    @Builder.Default
    private List<ProblemPage> problems = new ArrayList<>();

    public static ProblemPageResponse from(List<ProblemPageResponseDto> problems){
        return ProblemPageResponse.builder()
                .problems(problems.stream()
                        .map(ProblemPage::from)
                        .collect(Collectors.toList()))
                .build();
    }

}
