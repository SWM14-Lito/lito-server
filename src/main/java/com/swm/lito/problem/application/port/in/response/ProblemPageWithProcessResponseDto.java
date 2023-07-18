package com.swm.lito.problem.application.port.in.response;

import com.swm.lito.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
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
public class ProblemPageWithProcessResponseDto {

    private Long problemUserId;

    private Long problemId;

    private String subjectName;

    private String question;

    private boolean favorite;

    public static List<ProblemPageWithProcessResponseDto> from(List<ProblemPageWithProcessQResponseDto> dtos){
        return dtos.stream()
                .map(ProblemPageWithProcessResponseDto::from)
                .collect(Collectors.toList());
    }

    private static ProblemPageWithProcessResponseDto from(ProblemPageWithProcessQResponseDto dto){
        return ProblemPageWithProcessResponseDto.builder()
                .problemUserId(dto.getProblemUserId())
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .favorite(dto.isFavorite())
                .build();
    }
}
