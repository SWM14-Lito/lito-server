package com.swm.lito.batch.dto.response;

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
public class RecommendUserResponse {

    private String taskId;

    private String taskStatus;

    private List<RecommendUserResponseDto> data = new ArrayList<>();
}
