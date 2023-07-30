package com.swm.lito.core.problem.application.port.out;

import com.swm.lito.core.problem.domain.Favorite;
import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.user.domain.User;

import java.util.Optional;

public interface FavoriteQueryPort {

    boolean existsByUserAndProblem(User user, Problem problem);

    Optional<Favorite> findByUserAndProblem(User user, Problem problem);
}
