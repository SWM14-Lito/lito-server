package com.lito.core.problem.application.port.out;

import com.lito.core.problem.domain.ProblemUser;

public interface ProblemUserCommandPort {

    ProblemUser save(ProblemUser problemUser);
}
