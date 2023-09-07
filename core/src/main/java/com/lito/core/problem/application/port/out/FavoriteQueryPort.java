package com.lito.core.problem.application.port.out;

import com.lito.core.user.domain.User;
import com.lito.core.problem.domain.Favorite;
import com.lito.core.problem.domain.Problem;

import java.util.Optional;

public interface FavoriteQueryPort {

    Optional<Favorite> findByUserAndProblem(User user, Problem problem);
}
