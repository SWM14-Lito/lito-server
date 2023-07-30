package com.swm.lito.api.problem.adapter.in.response;

import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
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

    public static List<ProblemPageWithProcess> from(List<ProblemPageWithProcessQResponseDto> dtos){
        return dtos.stream()
                .map(ProblemPageWithProcess::from)
                .collect(Collectors.toList());
    }
    private static ProblemPageWithProcess from(ProblemPageWithProcessQResponseDto dto){
        return ProblemPageWithProcess.builder()
                .problemUserId(dto.getProblemUserId())
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .favorite(dto.isFavorite())
                .build();
    }
}
