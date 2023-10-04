package com.lito.core.problem.application.port.in;

import com.lito.core.common.security.AuthUser;
import com.lito.core.problem.application.port.in.request.ProblemSubmitRequestDto;
import com.lito.core.problem.application.port.in.response.ProblemSubmitResponseDto;
import com.lito.core.problem.domain.RecommendUser;

import java.util.List;

public interface ProblemCommandUseCase {

    ProblemSubmitResponseDto submit(AuthUser authUser, Long id, ProblemSubmitRequestDto requestDto);
    void createProblemUser(AuthUser authUser, Long id);

    void updateFavorite(AuthUser authUser, Long id);

    void saveRecommendUsers(List<RecommendUser> recommendUsers);
}
