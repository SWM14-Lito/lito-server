package com.swm.lito.problem.application.service;

import com.swm.lito.common.entity.BaseEntity;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.problem.ProblemErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.ProblemCommandUseCase;
import com.swm.lito.problem.application.port.in.request.ProblemUserSolvedRequestDto;
import com.swm.lito.problem.application.port.in.response.ProblemUserSolvedResponseDto;
import com.swm.lito.problem.application.port.out.FavoriteCommandPort;
import com.swm.lito.problem.application.port.out.FavoriteQueryPort;
import com.swm.lito.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.problem.application.port.out.ProblemUserQueryPort;
import com.swm.lito.problem.domain.Favorite;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemCommandService implements ProblemCommandUseCase {

    private final ProblemQueryPort problemQueryPort;
    private final ProblemUserQueryPort problemUserQueryPort;
    private final FavoriteCommandPort favoriteCommandPort;
    private final FavoriteQueryPort favoriteQueryPort;

    @Override
    public ProblemUserSolvedResponseDto createProblemUser(AuthUser authUser, Long id, ProblemUserSolvedRequestDto requestDto) {
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        ProblemUser problemUser = problemUserQueryPort.findByProblemAndUser(problem, user)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_INVALID));

        if(requestDto.getKeyword().equalsIgnoreCase(problem.getKeyword())){
            problemUser.changeStatus(ProblemStatus.COMPLETE);
            return ProblemUserSolvedResponseDto.from(true);
        }
        else{
            problemUser.addUnsolved();
            return ProblemUserSolvedResponseDto.from(false);
        }
    }

    @Override
    public void update(AuthUser authUser, Long id) {
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        ProblemUser problemUser = problemUserQueryPort.findByProblemAndUser(problem, user)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_INVALID));
        user.validateUser(authUser, problemUser.getUser());
        problemUser.changeStatus(problemUser.getProblemStatus());
    }

    @Override
    public void updateFavorite(AuthUser authUser, Long id) {
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));

        favoriteQueryPort.findByUserAndProblem(user, problem)
                        .ifPresentOrElse(
                                f -> f.setStatus(f.getStatus() == BaseEntity.Status.ACTIVE ?
                                        BaseEntity.Status.INACTIVE : BaseEntity.Status.ACTIVE),
                                () -> favoriteCommandPort.save(Favorite.createFavorite(user, problem))
                        );
    }
}
