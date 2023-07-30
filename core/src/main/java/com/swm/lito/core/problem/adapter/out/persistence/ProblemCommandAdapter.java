package com.swm.lito.core.problem.adapter.out.persistence;

import com.swm.lito.core.problem.application.port.out.FavoriteCommandPort;
import com.swm.lito.core.problem.application.port.out.ProblemUserCommandPort;
import com.swm.lito.core.problem.domain.Favorite;
import com.swm.lito.core.problem.domain.ProblemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProblemCommandAdapter implements FavoriteCommandPort, ProblemUserCommandPort {

    private final FavoriteRepository favoriteRepository;
    private final ProblemUserRepository problemUserRepository;

    @Override
    public void save(Favorite favorite) {
            favoriteRepository.save(favorite);
    }

    @Override
    public ProblemUser save(ProblemUser problemUser) {
        return problemUserRepository.save(problemUser);
    }
}
