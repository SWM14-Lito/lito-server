package com.lito.core.problem.application.service;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.problem.ProblemErrorCode;
import com.lito.core.common.security.AuthUser;
import com.lito.core.problem.application.port.in.ProblemCommandUseCase;
import com.lito.core.problem.application.port.in.request.ProblemSubmitRequestDto;
import com.lito.core.problem.application.port.in.response.ProblemSubmitResponseDto;
import com.lito.core.problem.application.port.out.*;
import com.lito.core.problem.domain.Favorite;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.problem.domain.RecommendUser;
import com.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemCommandService implements ProblemCommandUseCase {

    private final ProblemQueryPort problemQueryPort;
    private final ProblemUserCommandPort problemUserCommandPort;
    private final ProblemUserQueryPort problemUserQueryPort;
    private final FavoriteCommandPort favoriteCommandPort;
    private final FavoriteQueryPort favoriteQueryPort;
    private final RecommendUserCommandPort recommendUserCommandPort;

    @Override
    public void createProblemUser(AuthUser authUser, Long id) {
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        problemUserQueryPort.findByProblemAndUser(problem, user)
                .orElseGet(() -> problemUserCommandPort.save(ProblemUser.createProblemUser(problem, user)));
    }

    @Override
    public ProblemSubmitResponseDto submit(AuthUser authUser, Long id, ProblemSubmitRequestDto requestDto) {
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        ProblemUser problemUser = problemUserQueryPort.findByProblemAndUser(problem, user)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_INVALID));

        if(requestDto.getKeyword().equalsIgnoreCase(problem.getKeyword())){
            problemUser.changeStatus(problemUser.getProblemStatus());
            return ProblemSubmitResponseDto.from(true);
        }
        else{
            problemUser.addUnsolved();
            return ProblemSubmitResponseDto.from(false);
        }
    }


    @Override
    public void updateFavorite(AuthUser authUser, Long id) {
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));

        favoriteQueryPort.findByUserAndProblem(user, problem)
                        .ifPresentOrElse(
                                f -> f.changeStatus(f.getStatus() == BaseEntity.Status.ACTIVE ?
                                        BaseEntity.Status.INACTIVE : BaseEntity.Status.ACTIVE),
                                () -> favoriteCommandPort.save(Favorite.createFavorite(user, problem))
                        );
    }

    @Override
    public void saveRecommendUsers(List<RecommendUser> recommendUsers){
        recommendUserCommandPort.saveRecommendUsers(recommendUsers);
    }
}
