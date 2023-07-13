package com.swm.lito.problem.adapter.in.presentation;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.adapter.in.response.ProblemPage;
import com.swm.lito.problem.adapter.in.response.ProblemPageResponse;
import com.swm.lito.problem.adapter.in.response.ProblemUserResponse;
import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.swm.lito.problem.domain.enums.ProblemStatus.toEnum;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemQueryUseCase problemQueryUseCase;

    @GetMapping("/users")
    public ProblemUserResponse findProblemUser(@AuthenticationPrincipal AuthUser authUser){
        return ProblemUserResponse.from(problemQueryUseCase.findProblemUser(authUser));
    }

    @GetMapping
    public ProblemPageResponse findProblemPage(@AuthenticationPrincipal AuthUser authUser,
                                               @RequestParam(required = false) Long lastProblemId,
                                               @RequestParam(required = false) String subjectName,
                                               @RequestParam(required = false) ProblemStatus problemStatus,
                                               @RequestParam(required = false) String query,
                                               @RequestParam Integer size){

        return ProblemPageResponse.from(ProblemPage.from(problemQueryUseCase.findProblemPage
                (authUser, lastProblemId, subjectName, problemStatus, query, size)));
    }
}
