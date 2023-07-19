package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.FavoriteCommandPort;
import com.swm.lito.problem.domain.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProblemCommandAdapter implements FavoriteCommandPort {

    private final FavoriteRepository favoriteRepository;

    @Override
    public void save(Favorite favorite) {
            favoriteRepository.save(favorite);
    }
}
