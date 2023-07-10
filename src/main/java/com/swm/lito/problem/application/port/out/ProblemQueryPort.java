package com.swm.lito.problem.application.port.out;


import com.swm.lito.problem.domain.Problem;

import java.util.Optional;

public interface ProblemQueryPort {

    Optional<Problem> findById(Long id);

}
