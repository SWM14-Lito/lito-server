package com.swm.lito.problem.adapter.in.response;

import com.swm.lito.problem.application.port.in.response.ProblemPageWithFavoriteResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
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
public class ProblemPageWithFavorite {

    private Long favoriteId;

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    public static List<ProblemPageWithFavorite> from(List<ProblemPageWithFavoriteQResponseDto> dtos){
        return dtos.stream()
                .map(ProblemPageWithFavorite::from)
                .collect(Collectors.toList());
    }

    private static ProblemPageWithFavorite from(ProblemPageWithFavoriteQResponseDto dto){
        return ProblemPageWithFavorite.builder()
                .favoriteId(dto.getFavoriteId())
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .problemStatus(dto.getProblemStatus().getName())
                .build();
    }
}
