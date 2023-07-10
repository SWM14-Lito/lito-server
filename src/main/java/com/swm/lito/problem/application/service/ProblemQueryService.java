package com.swm.lito.problem.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.problem.ProblemErrorCode;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.problem.application.port.out.FavoriteQueryPort;
import com.swm.lito.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.problem.application.port.out.UserProblemQueryPort;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemQueryService implements ProblemQueryUseCase{

    private final UserQueryPort userQueryPort;
    private final ProblemQueryPort problemQueryPort;
    private final UserProblemQueryPort userProblemQueryPort;
    private final FavoriteQueryPort favoriteQueryPort;

    @Override
    public ProblemUserResponseDto findProblemUser(AuthUser authUser) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        ProblemUser problemUser = userProblemQueryPort.findFirstUserProblem(user)
                .orElse(null);
        Problem problem = problemUser != null ? problemQueryPort.findById(problemUser.getProblem().getId())
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND)) : null;
        boolean favorite = problemUser != null && favoriteQueryPort.existsByUserAndProblem(user, problem);
        return problem != null ? ProblemUserResponseDto.of(user, problem, favorite)
                : ProblemUserResponseDto.of(user);
    }
}
