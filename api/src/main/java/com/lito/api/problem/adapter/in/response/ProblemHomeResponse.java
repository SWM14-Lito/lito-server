package com.lito.api.problem.adapter.in.response;

import com.lito.core.problem.application.port.in.response.ProblemHomeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemHomeResponse {

    private Long userId;

    private String profileImgUrl;

    private String nickname;

    private ProcessProblemResponse processProblem;

    @Builder.Default
    private List<RecommendUserResponse> recommendProblems = new ArrayList<>();

    public static ProblemHomeResponse from(ProblemHomeResponseDto responseDto){
        return ProblemHomeResponse.builder()
                .userId(responseDto.getUserId())
                .profileImgUrl(responseDto.getProfileImgUrl())
                .nickname(responseDto.getNickname())
                .processProblem(ProcessProblemResponse.from(responseDto.getProcessProblemResponseDto()))
                .recommendProblems(RecommendUserResponse.from(responseDto.getRecommendUserResponseDtos()))
                .build();
    }
}
