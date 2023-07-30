package com.swm.lito.batch.dto.request;

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
public class ProblemUserRequest {

    private Long maxUserId;

    private Long maxProblemId;

    @Builder.Default
    private List<ProblemUserRequestDto> data = new ArrayList<>();

    public static ProblemUserRequest of(Long maxUserId, Long maxProblemId, List<ProblemUserRequestDto> requestDtos){
        return ProblemUserRequest.builder()
                .maxUserId(maxUserId)
                .maxProblemId(maxProblemId)
                .data(requestDtos)
                .build();
    }
}
