package com.swm.lito.problem.adapter.in.presentation;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.adapter.in.response.*;
import com.swm.lito.problem.application.port.in.ProblemCommandUseCase;
import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/problems")
public class ProblemController {

    private final ProblemCommandUseCase problemCommandUseCase;
    private final ProblemQueryUseCase problemQueryUseCase;

    @GetMapping("/{id}")
    public ProblemResponse find(@AuthenticationPrincipal AuthUser authUser,
                                @PathVariable Long id){
        return ProblemResponse.from(problemQueryUseCase.find(authUser, id));
    }

    @GetMapping
    public ProblemPageResponse findProblemPage(@AuthenticationPrincipal AuthUser authUser,
                                               @RequestParam(required = false) Long lastProblemId,
                                               @RequestParam(required = false) Long subjectId,
                                               @RequestParam(required = false) ProblemStatus problemStatus,
                                               @RequestParam(required = false) String query,
                                               @RequestParam(required = false, defaultValue = "10") Integer size){

        return ProblemPageResponse.from(ProblemPage.from(problemQueryUseCase.findProblemPage
                (authUser, lastProblemId, subjectId, problemStatus, query, size)));
    }


    @GetMapping("/users")
    public ProblemUserResponse findProblemUser(@AuthenticationPrincipal AuthUser authUser){
        return ProblemUserResponse.from(problemQueryUseCase.findProblemUser(authUser));
    }

    @GetMapping("/process-status")
    public ProblemPageWithProcessResponse findProblemPageWithProcess(@AuthenticationPrincipal AuthUser authUser,
                                                                     @RequestParam(required = false) Long lastProblemUserId,
                                                                     @RequestParam(required = false, defaultValue = "10") Integer size){
        return ProblemPageWithProcessResponse.from(ProblemPageWithProcess.from(problemQueryUseCase.findProblemPageWithProcess
                (authUser, lastProblemUserId, size)));
    }

    @PatchMapping("/{id}")
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @PathVariable Long id){
        problemCommandUseCase.update(authUser, id);
    }
}
