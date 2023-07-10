package com.swm.lito.problem.application.port.out;

import com.swm.lito.problem.domain.Problem;
import com.swm.lito.user.domain.User;

public interface FavoriteQueryPort {

    boolean existsByUserAndProblem(User user, Problem problem);
}
