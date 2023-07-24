package com.swm.lito.problem.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.request.ProblemUserSolvedRequestDto;
import com.swm.lito.problem.application.port.in.response.ProblemUserSolvedResponseDto;

public interface ProblemCommandUseCase {

    ProblemUserSolvedResponseDto createProblemUser(AuthUser authUser, Long id, ProblemUserSolvedRequestDto requestDto);
    void update(AuthUser authUser, Long id);

    void updateFavorite(AuthUser authUser, Long id);
}
