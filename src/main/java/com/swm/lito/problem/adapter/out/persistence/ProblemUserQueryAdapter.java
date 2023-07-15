package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.ProblemUserQueryPort;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemUserQueryAdapter implements ProblemUserQueryPort {

    private final ProblemUserRepository problemUserRepository;


    @Override
    public Optional<ProblemUser> findFirstProblemUser(User user) {
        return problemUserRepository.findFirstByUserAndProblemStatusOrderByCreatedAtDesc(user, ProblemStatus.PROCESS);
    }
}
