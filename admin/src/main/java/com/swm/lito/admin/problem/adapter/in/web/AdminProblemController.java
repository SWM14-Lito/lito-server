package com.swm.lito.admin.problem.adapter.in.web;

import com.swm.lito.admin.problem.adapter.in.request.PostRequest;
import com.swm.lito.core.admin.application.port.in.AdminProblemCommandUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/problems")
public class AdminProblemController {

    private final AdminProblemCommandUseCase adminProblemCommandUseCase;

    @PostMapping
    public void create(@RequestBody @Valid PostRequest postRequest){
        adminProblemCommandUseCase.create(postRequest.toRequestDto());
    }
}
