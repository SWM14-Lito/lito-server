package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.domain.Favorite;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

    boolean existsByUserAndProblem(User user, Problem problem);

}
