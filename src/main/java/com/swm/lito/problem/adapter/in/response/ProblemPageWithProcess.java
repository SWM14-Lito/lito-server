package com.swm.lito.problem.adapter.in.response;

import com.swm.lito.problem.application.port.in.response.ProblemPageWithProcessResponseDto;
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
public class ProblemPageWithProcess {

    private Long problemUserId;

    private Long problemId;

    private String subjectName;

    private String question;

    private boolean favorite;

    public static List<ProblemPageWithProcess> from(List<ProblemPageWithProcessResponseDto> dtos){
        return dtos.stream()
                .map(ProblemPageWithProcess::from)
                .collect(Collectors.toList());
    }
    private static ProblemPageWithProcess from(ProblemPageWithProcessResponseDto dto){
        return ProblemPageWithProcess.builder()
                .problemUserId(dto.getProblemUserId())
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .favorite(dto.isFavorite())
                .build();
    }
}
