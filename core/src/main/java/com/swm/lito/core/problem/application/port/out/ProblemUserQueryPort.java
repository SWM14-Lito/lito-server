package com.swm.lito.core.problem.application.port.out;

import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.problem.domain.ProblemUser;
import com.swm.lito.core.user.domain.User;

import java.util.Optional;

public interface ProblemUserQueryPort {

    Optional<ProblemUser> findFirstProblemUser(User user);
    Optional<ProblemUser> findByProblemAndUser(Problem problem, User user);
}
