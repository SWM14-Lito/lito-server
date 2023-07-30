package com.swm.lito.core.problem.application.port.out;

import com.swm.lito.core.problem.domain.ProblemUser;

public interface ProblemUserCommandPort {

    ProblemUser save(ProblemUser problemUser);
}
