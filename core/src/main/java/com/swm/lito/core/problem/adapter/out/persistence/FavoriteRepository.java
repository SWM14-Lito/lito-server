package com.swm.lito.core.problem.adapter.out.persistence;

import com.swm.lito.core.problem.domain.Favorite;
import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

    boolean existsByUserAndProblem(User user, Problem problem);

    Optional<Favorite> findByUserAndProblem(User user, Problem problem);

}
