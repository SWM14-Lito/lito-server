package com.lito.batch.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendUserResponse {

    private String taskId;

    private String taskStatus;

    private List<RecommendUserResponseDto> data = new ArrayList<>();
}
