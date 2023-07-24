package com.swm.lito.problem.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.request.ProblemUserSubmitRequestDto;
import com.swm.lito.problem.application.port.in.response.ProblemSubmitResponseDto;

public interface ProblemCommandUseCase {

    ProblemSubmitResponseDto submit(AuthUser authUser, Long id, ProblemUserSubmitRequestDto requestDto);
    void createProblemUser(AuthUser authUser, Long id);

    void updateFavorite(AuthUser authUser, Long id);
}
