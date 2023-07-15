package com.swm.lito.problem.application.port.out;

import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.user.domain.User;

import java.util.Optional;

public interface ProblemUserQueryPort {

    Optional<ProblemUser> findFirstProblemUser(User user);
}
