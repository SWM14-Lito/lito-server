package com.lito.core.problem.application.port.out;

import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.user.domain.User;
import com.lito.core.problem.domain.Problem;

import java.util.Optional;

public interface ProblemUserQueryPort {

    Optional<ProblemUser> findFirstProblemUser(User user);
    Optional<ProblemUser> findByProblemAndUser(Problem problem, User user);
}
