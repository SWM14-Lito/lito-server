package com.swm.lito.problem.application.port.out;

import com.swm.lito.problem.domain.Favorite;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.user.domain.User;

import java.util.Optional;

public interface FavoriteQueryPort {

    boolean existsByUserAndProblem(User user, Problem problem);

    Optional<Favorite> findByUserAndProblem(User user, Problem problem);
}
