package com.swm.lito.api.problem.adapter.in.web;

import com.swm.lito.api.problem.adapter.in.response.*;
import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.api.problem.adapter.in.request.ProblemSubmitRequest;
import com.swm.lito.core.problem.application.port.in.ProblemCommandUseCase;
import com.swm.lito.core.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.core.problem.domain.enums.ProblemStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                                               @RequestParam(required = false) Long subjectId,
                                               @RequestParam(required = false) ProblemStatus problemStatus,
                                               @RequestParam(required = false) String query,
                                               Pageable pageable){
        Page<ProblemPageQueryDslResponseDto> responseDtos = problemQueryUseCase.findProblemPage
                (authUser, subjectId, problemStatus, query, pageable);
        return ProblemPageResponse.of(ProblemPage.from(responseDtos.getContent()), responseDtos.getTotalElements());
    }

    @GetMapping("/users")
    public ProblemHomeResponse findHome(@AuthenticationPrincipal AuthUser authUser){
        return ProblemHomeResponse.from(problemQueryUseCase.findHome(authUser));
    }

    @GetMapping("/process-status")
    public ProblemPageWithProcessResponse findProblemPageWithProcess(@AuthenticationPrincipal AuthUser authUser,
                                                                     Pageable pageable){
        Page<ProblemPageWithProcessQResponseDto> responseDtos = problemQueryUseCase.findProblemPageWithProcess(authUser, pageable);
        return ProblemPageWithProcessResponse.of(ProblemPageWithProcess.from(responseDtos.getContent()), responseDtos.getTotalElements());
    }

    @GetMapping("/favorites")
    public ProblemPageWithFavoriteResponse findProblemPageWithFavorite(@AuthenticationPrincipal AuthUser authUser,
                                                                       @RequestParam(required = false) Long subjectId,
                                                                       @RequestParam(required = false) ProblemStatus problemStatus,
                                                                       Pageable pageable){
        Page<ProblemPageWithFavoriteQResponseDto> responseDtos = problemQueryUseCase.findProblemPageWithFavorite(authUser, subjectId, problemStatus, pageable);
        return ProblemPageWithFavoriteResponse.of(ProblemPageWithFavorite.from(responseDtos.getContent()), responseDtos.getTotalElements());
    }

    @PostMapping("/{id}")
    public void createProblemUser(@AuthenticationPrincipal AuthUser authUser,
                                  @PathVariable Long id){
        problemCommandUseCase.createProblemUser(authUser, id);
    }

    @PatchMapping("/{id}/users")
    public ProblemSubmitResponse submit(@AuthenticationPrincipal AuthUser authUser,
                                        @PathVariable Long id,
                                        @RequestBody @Valid ProblemSubmitRequest request){
        return ProblemSubmitResponse.from(problemCommandUseCase.submit(authUser, id, request.toRequestDto()));
    }


    @PatchMapping("/{id}/favorites")
    public void updateFavorite(@AuthenticationPrincipal AuthUser authUser,
                               @PathVariable Long id){
        problemCommandUseCase.updateFavorite(authUser, id);
    }
}
