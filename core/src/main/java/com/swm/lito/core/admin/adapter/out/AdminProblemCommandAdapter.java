package com.swm.lito.core.admin.adapter.out;

import com.swm.lito.core.admin.application.port.out.AdminProblemCommandPort;
import com.swm.lito.core.problem.domain.Problem;
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
