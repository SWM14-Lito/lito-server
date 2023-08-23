package com.swm.lito.api.problem.adapter.in.response;

import com.swm.lito.core.problem.application.port.in.response.ProcessProblemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessProblemResponse {

    private Long problemId;

    private String subjectName;

    private String question;

    private Boolean favorite;

    public static ProcessProblemResponse from(ProcessProblemResponseDto responseDto){
        return ProcessProblemResponse.builder()
                .problemId(responseDto.getProblemId())
                .subjectName(responseDto.getSubjectName())
                .question(responseDto.getQuestion())
                .favorite(responseDto.getFavorite())
                .build();
    }
}
