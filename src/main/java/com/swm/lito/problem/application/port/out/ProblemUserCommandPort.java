package com.swm.lito.problem.application.port.out;

import com.swm.lito.problem.domain.ProblemUser;

public interface ProblemUserCommandPort {

    ProblemUser save(ProblemUser problemUser);
}
