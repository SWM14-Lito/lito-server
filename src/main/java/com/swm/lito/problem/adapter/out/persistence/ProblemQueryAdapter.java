package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.problem.domain.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemQueryAdapter implements ProblemQueryPort {

    private final ProblemRepository problemRepository;


    @Override
    public Optional<Problem> findById(Long id) {
        return problemRepository.findById(id);
    }
}
