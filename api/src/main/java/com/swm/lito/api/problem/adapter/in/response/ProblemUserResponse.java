package com.swm.lito.api.problem.adapter.in.response;

import com.swm.lito.core.problem.application.port.in.response.ProblemUserResponseDto;
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
public class ProblemUserResponse {

    private Long userId;

    private String profileImgUrl;

    private String nickname;

    private ProcessProblemResponse processProblem;

    private List<RecommendUserResponse> recommendProblems = new ArrayList<>();

    public static ProblemUserResponse from(ProblemUserResponseDto responseDto){
        return ProblemUserResponse.builder()
                .userId(responseDto.getUserId())
                .profileImgUrl(responseDto.getProfileImgUrl())
                .nickname(responseDto.getNickname())
                .processProblem(ProcessProblemResponse.from(responseDto.getProcessProblemResponseDto()))
                .recommendProblems(RecommendUserResponse.from(responseDto.getRecommendUserResponseDtos()))
                .build();
    }
}
