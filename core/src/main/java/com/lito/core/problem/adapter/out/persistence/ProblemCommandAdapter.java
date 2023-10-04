package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.application.port.out.FavoriteCommandPort;
import com.lito.core.problem.application.port.out.ProblemUserCommandPort;
import com.lito.core.problem.application.port.out.RecommendUserCommandPort;
import com.lito.core.problem.domain.Favorite;
import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.problem.domain.RecommendUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemCommandAdapter implements FavoriteCommandPort, ProblemUserCommandPort, RecommendUserCommandPort {

    private final FavoriteRepository favoriteRepository;
    private final ProblemUserRepository problemUserRepository;
    private final RecommendUserRepository recommendUserRepository;

    @Override
    public void save(Favorite favorite) {
            favoriteRepository.save(favorite);
    }

    @Override
    public ProblemUser save(ProblemUser problemUser) {
        return problemUserRepository.save(problemUser);
    }

    @Override
    public void saveRecommendUsers(List<RecommendUser> recommendUsers){
        recommendUserRepository.saveRecommendUsers(recommendUsers);
    }
}
