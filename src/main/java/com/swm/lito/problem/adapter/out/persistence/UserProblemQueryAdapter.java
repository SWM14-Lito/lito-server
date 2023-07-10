package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.UserProblemQueryPort;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserProblemQueryAdapter implements UserProblemQueryPort {

    private final UserProblemRepository userProblemRepository;


    @Override
    public Optional<ProblemUser> findFirstUserProblem(User user) {
        return userProblemRepository.findFirstByUserAndProblemStatusOrderByCreatedAtDesc(user, ProblemStatus.PROCESS);
    }
}
