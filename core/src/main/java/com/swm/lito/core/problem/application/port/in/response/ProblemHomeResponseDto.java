package com.swm.lito.core.problem.application.port.in.response;

import com.swm.lito.core.user.domain.User;
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
public class ProblemHomeResponseDto {

    private Long userId;

    private String profileImgUrl;

    private String nickname;

    private ProcessProblemResponseDto processProblemResponseDto;

    @Builder.Default
    private List<RecommendUserResponseDto> recommendUserResponseDtos = new ArrayList<>();

    public static ProblemHomeResponseDto of(User user, ProcessProblemResponseDto processProblemResponseDto, List<RecommendUserResponseDto> recommendUserResponseDtos){
        return ProblemHomeResponseDto.builder()
                .userId(user.getId())
                .profileImgUrl(user.getProfileImgUrl())
                .nickname(user.getNickname())
                .processProblemResponseDto(processProblemResponseDto)
                .recommendUserResponseDtos(recommendUserResponseDtos)
                .build();
    }

    public static ProblemHomeResponseDto of(User user, List<RecommendUserResponseDto> recommendUserResponseDtos){
        return ProblemHomeResponseDto.builder()
                .userId(user.getId())
                .profileImgUrl(user.getProfileImgUrl())
                .nickname(user.getNickname())
                .processProblemResponseDto(ProcessProblemResponseDto.builder().build())
                .recommendUserResponseDtos(recommendUserResponseDtos)
                .build();
    }
}
