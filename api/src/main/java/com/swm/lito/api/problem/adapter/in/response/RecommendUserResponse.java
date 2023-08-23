package com.swm.lito.api.problem.adapter.in.response;

import com.swm.lito.core.problem.application.port.in.response.RecommendUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendUserResponse {

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    private Boolean favorite;

    public static List<RecommendUserResponse> from(List<RecommendUserResponseDto> responseDtos){
        return responseDtos.stream()
                .map(RecommendUserResponse::from)
                .collect(Collectors.toList());
    }

    private static RecommendUserResponse from(RecommendUserResponseDto responseDto){
        return RecommendUserResponse.builder()
                .problemId(responseDto.getProblemId())
                .subjectName(responseDto.getSubjectName())
                .question(responseDto.getQuestion())
                .problemStatus(responseDto.getProblemStatus())
                .favorite(responseDto.getFavorite())
                .build();
    }
}
