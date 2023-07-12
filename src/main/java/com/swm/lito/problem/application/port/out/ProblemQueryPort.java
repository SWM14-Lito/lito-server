package com.swm.lito.problem.application.port.out;


import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.problem.domain.enums.ProblemStatus;

import java.util.List;
import java.util.Optional;

public interface ProblemQueryPort {

    Optional<Problem> findProblemById(Long id);

    List<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long lastProblemId, String subjectName,
                                                         ProblemStatus problemStatus, String query, Integer size);
}
