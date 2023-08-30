package com.lito.core.problem.application.port.in.response;

import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
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
public class ProblemPageWithFavoriteResponseDto {

    private Long favoriteId;

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;


    public static List<ProblemPageWithFavoriteResponseDto> from(List<ProblemPageWithFavoriteQResponseDto> dtos){
        return dtos.stream()
                .map(ProblemPageWithFavoriteResponseDto::from)
                .collect(Collectors.toList());
    }

    private static ProblemPageWithFavoriteResponseDto from(ProblemPageWithFavoriteQResponseDto dto){
        return ProblemPageWithFavoriteResponseDto.builder()
                .favoriteId(dto.getFavoriteId())
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .problemStatus(dto.getProblemStatus().getName())
                .build();
    }
}
