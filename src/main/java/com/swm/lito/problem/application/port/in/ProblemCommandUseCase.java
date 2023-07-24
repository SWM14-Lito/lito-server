package com.swm.lito.problem.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.request.ProblemSubmitRequestDto;
import com.swm.lito.problem.application.port.in.response.ProblemSubmitResponseDto;

public interface ProblemCommandUseCase {

    ProblemSubmitResponseDto submit(AuthUser authUser, Long id, ProblemSubmitRequestDto requestDto);
    void createProblemUser(AuthUser authUser, Long id);

    void updateFavorite(AuthUser authUser, Long id);
}
