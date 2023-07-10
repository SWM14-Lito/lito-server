package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.FavoriteQueryPort;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FavoriteQueryAdapter implements FavoriteQueryPort {

    private final FavoriteRepository favoriteRepository;


    @Override
    public boolean existsByUserAndProblem(User user, Problem problem) {
        return favoriteRepository.existsByUserAndProblem(user, problem);
    }
}
