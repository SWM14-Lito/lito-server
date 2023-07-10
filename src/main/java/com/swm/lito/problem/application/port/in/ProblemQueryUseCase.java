package com.swm.lito.problem.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;

public interface ProblemQueryUseCase {

    ProblemUserResponseDto findProblemUser(AuthUser authUser);
}
