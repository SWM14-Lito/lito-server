package com.swm.lito.problem.adapter.in.presentation;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.adapter.in.response.ProblemUserResponse;
import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemQueryUseCase problemQueryUseCase;

    @GetMapping("/users")
    public ProblemUserResponse findProblemUser(@AuthenticationPrincipal AuthUser authUser){
        return ProblemUserResponse.from(problemQueryUseCase.findProblemUser(authUser));
    }
}
