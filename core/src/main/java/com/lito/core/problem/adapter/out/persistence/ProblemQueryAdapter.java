package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.application.port.out.FavoriteQueryPort;
import com.lito.core.problem.application.port.out.ProblemQueryPort;
import com.lito.core.problem.application.port.out.ProblemUserQueryPort;
import com.lito.core.problem.application.port.out.RecommendUserQueryPort;
import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.problem.domain.RecommendUser;
import com.lito.core.user.domain.User;
import com.lito.core.problem.domain.Favorite;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.enums.ProblemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemQueryAdapter implements ProblemQueryPort, FavoriteQueryPort, ProblemUserQueryPort, RecommendUserQueryPort {

    private final ProblemRepository problemRepository;
    private final FavoriteRepository favoriteRepository;
    private final ProblemUserRepository problemUserRepository;
    private final RecommendUserRepository recommendUserRepository;

    @Override
    public Optional<Problem> findProblemWithFaqById(Long id) {
        return problemRepository.findProblemWithFaqById(id);
    }

    @Override
    public Optional<Problem> findProblemById(Long id) {
        return problemRepository.findProblemById(id);
    }

    @Override
    public Page<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                                String query, Pageable pageable) {
        return problemRepository.findProblemPage(userId, subjectId, problemStatus, query, pageable);
    }

    @Override
    public Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Pageable pageable) {
        return problemRepository.findProblemPageWithProcess(userId, pageable);
    }

    @Override
    public Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                                                 Pageable pageable) {
        return problemRepository.findProblemPageWithFavorite(userId, subjectId, problemStatus, pageable);
    }

    @Override
    public Optional<Favorite> findByUserAndProblem(User user, Problem problem) {
        return favoriteRepository.findByUserAndProblem(user, problem);
    }



    @Override
    public Optional<ProblemUser> findFirstProblemUser(User user) {
        return problemUserRepository.findFirstByUserAndProblemStatusOrderByCreatedAtDesc(user, ProblemStatus.PROCESS);
    }

    @Override
    public Optional<ProblemUser> findByProblemAndUser(Problem problem, User user) {
        return problemUserRepository.findByProblemAndUser(problem, user);
    }

    @Override
    public List<RecommendUser> findRecommendUsers(Long userId){
        return recommendUserRepository.findTop3ByUserIdOrderByCreatedAtDesc(userId);
    }
}
