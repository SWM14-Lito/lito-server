package com.swm.lito.problem.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.problem.ProblemErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.problem.application.port.in.response.ProblemPageResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.problem.application.port.out.FavoriteQueryPort;
import com.swm.lito.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.problem.application.service.comparator.ProblemStatusComparator;
import com.swm.lito.problem.application.port.out.ProblemUserQueryPort;
import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemQueryService implements ProblemQueryUseCase{

    private final ProblemQueryPort problemQueryPort;
    private final ProblemUserQueryPort problemUserQueryPort;
    private final FavoriteQueryPort favoriteQueryPort;

    @Override
    public ProblemResponseDto find(Long id){
        Problem problem = problemQueryPort.findProblemWithFaqById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        return ProblemResponseDto.from(problem);
    }

    @Override
    public List<ProblemPageResponseDto> findProblemPage(AuthUser authUser, Long lastProblemId, Long subjectId,
                                                        ProblemStatus problemStatus, String query, Integer size) {
        List<ProblemPageQueryDslResponseDto> queryDslResponseDtos = problemQueryPort.findProblemPage
                (authUser.getUserId(), lastProblemId, subjectId, query, size);

        //풀이완료 정렬
        if(problemStatus == ProblemStatus.COMPLETE){
            return ProblemPageResponseDto.from(queryDslResponseDtos
                    .stream()
                    .filter(p -> p.getProblemStatus() == ProblemStatus.COMPLETE)
                    .collect(Collectors.toList()));
        }
        //풀지않음 정렬
        else if(problemStatus == ProblemStatus.NOT_SEEN){
            return ProblemPageResponseDto.from(queryDslResponseDtos
                    .stream()
                    .filter(p -> p.getProblemStatus() != ProblemStatus.COMPLETE)
                    .sorted(new ProblemStatusComparator())
                    .collect(Collectors.toList()));
        }

        //기본 정렬
        return ProblemPageResponseDto.from(queryDslResponseDtos
                .stream()
                .sorted(new ProblemStatusComparator())
                .collect(Collectors.toList()));
    }

    @Override
    public ProblemUserResponseDto findProblemUser(AuthUser authUser) {
        User user = authUser.getUser();
        ProblemUser problemUser = problemUserQueryPort.findFirstProblemUser(user)
                .orElse(null);
        Problem problem = problemUser != null ? problemQueryPort.findProblemById(problemUser.getProblem().getId())
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND)) : null;
        boolean favorite = problemUser != null && favoriteQueryPort.existsByUserAndProblem(user, problem);
        return problem != null ? ProblemUserResponseDto.of(user, problem, favorite)
                : ProblemUserResponseDto.of(user);
    }
}
