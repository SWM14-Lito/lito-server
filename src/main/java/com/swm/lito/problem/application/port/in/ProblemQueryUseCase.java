package com.swm.lito.problem.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.response.ProblemPageResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.problem.domain.enums.ProblemStatus;

import java.util.List;

public interface ProblemQueryUseCase {

    ProblemResponseDto find(Long id);
    List<ProblemPageResponseDto> findProblemPage(AuthUser authUser, Long lastProblemId, String subjectName,
                                                 ProblemStatus problemStatus, String query, Integer size);
    ProblemUserResponseDto findProblemUser(AuthUser authUser);
}
