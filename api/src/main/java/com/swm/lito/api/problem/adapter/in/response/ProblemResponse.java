package com.swm.lito.api.problem.adapter.in.response;

import com.swm.lito.core.problem.application.port.in.response.FaqResponseDto;
import com.swm.lito.core.problem.application.port.in.response.ProblemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemResponse {

    private Long problemId;

    private String problemQuestion;

    private String problemAnswer;

    private String problemKeyword;

    private String problemStatus;

    private boolean favorite;

    private List<FaqResponseDto> faqs;

    public static ProblemResponse from(ProblemResponseDto dto){
        return ProblemResponse.builder()
                .problemId(dto.getProblemId())
                .problemQuestion(dto.getProblemQuestion())
                .problemAnswer(dto.getProblemAnswer())
                .problemKeyword(dto.getProblemKeyword())
                .problemStatus(dto.getProblemStatus())
                .favorite(dto.isFavorite())
                .faqs(dto.getFaqResponseDtos())
                .build();

    }
}
