package com.lito.core.problem.application.port.in.response;

import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
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
public class ProblemPageResponseDto {

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    private boolean favorite;

    public static List<ProblemPageResponseDto> from(List<ProblemPageQueryDslResponseDto> dtos){
        return dtos.stream()
                .map(ProblemPageResponseDto::from)
                .collect(Collectors.toList());
    }
    private static ProblemPageResponseDto from(ProblemPageQueryDslResponseDto dto){
        return ProblemPageResponseDto.builder()
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .problemStatus(dto.getProblemStatus().getName())
                .favorite(dto.isFavorite())
                .build();
    }
}
