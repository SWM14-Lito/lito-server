package com.lito.core.admin.adapter.out;

import com.lito.core.admin.application.port.out.AdminProblemCommandPort;
import com.lito.core.problem.domain.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminProblemCommandAdapter implements AdminProblemCommandPort {

    private final AdminProblemRepository adminProblemRepository;


    @Override
    public void save(Problem problem) {
        adminProblemRepository.save(problem);
    }
}
