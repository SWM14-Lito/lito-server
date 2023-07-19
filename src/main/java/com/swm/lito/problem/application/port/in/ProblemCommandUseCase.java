package com.swm.lito.problem.application.port.in;

import com.swm.lito.common.security.AuthUser;

public interface ProblemCommandUseCase {

    void update(AuthUser authUser, Long id);

    void updateFavorite(AuthUser authUser, Long id);
}
