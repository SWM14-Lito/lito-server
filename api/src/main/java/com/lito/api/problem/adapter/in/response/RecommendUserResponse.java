package com.lito.api.problem.adapter.in.response;

import com.lito.core.problem.application.port.in.response.RecommendUserResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecommendUserResponse {

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    private boolean favorite;

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
                .favorite(responseDto.isFavorite())
                .build();
    }
}
