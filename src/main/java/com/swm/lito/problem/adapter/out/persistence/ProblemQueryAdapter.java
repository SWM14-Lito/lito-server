package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.FavoriteQueryPort;
import com.swm.lito.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.problem.application.port.out.ProblemUserQueryPort;
import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.problem.domain.Favorite;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemQueryAdapter implements ProblemQueryPort, FavoriteQueryPort, ProblemUserQueryPort {

    private final ProblemRepository problemRepository;
    private final FavoriteRepository favoriteRepository;
    private final ProblemUserRepository problemUserRepository;

    @Override
    public Optional<Problem> findProblemWithFaqById(Long id) {
        return problemRepository.findProblemWithFaqById(id);
    }

    @Override
    public Optional<Problem> findProblemById(Long id) {
        return problemRepository.findProblemById(id);
    }

    @Override
    public List<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long lastProblemId, Long subjectId,
                                                                ProblemStatus problemStatus, String query, Integer size) {
        return problemRepository.findProblemPage(userId, lastProblemId, subjectId, problemStatus, query, size);
    }

    @Override
    public List<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Long lastProblemUserId, Integer size) {
        return problemRepository.findProblemPageWithProcess(userId, lastProblemUserId, size);
    }

    @Override
    public List<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long lastFavoriteId, Long subjectId,
                                                                                 ProblemStatus problemStatus, Integer size) {
        return problemRepository.findProblemPageWithFavorite(userId, lastFavoriteId, subjectId, problemStatus, size);
    }

    @Override
    public boolean existsByUserAndProblem(User user, Problem problem) {
        return favoriteRepository.existsByUserAndProblem(user, problem);
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
}
