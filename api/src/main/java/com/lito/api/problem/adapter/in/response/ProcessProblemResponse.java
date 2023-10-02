package com.lito.api.problem.adapter.in.response;

import com.lito.core.problem.application.port.in.response.ProcessProblemResponseDto;
import com.lito.core.problem.domain.enums.ProblemStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProcessProblemResponse {

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    private Boolean favorite;

    public static ProcessProblemResponse from(ProcessProblemResponseDto responseDto){
        return ProcessProblemResponse.builder()
                .problemId(responseDto.getProblemId())
                .subjectName(responseDto.getSubjectName())
                .question(responseDto.getQuestion())
                .problemStatus(responseDto.getProblemId() != null ? ProblemStatus.PROCESS.getName() : null)
                .favorite(responseDto.isFavorite())
                .build();
    }
}
