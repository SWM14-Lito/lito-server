package com.lito.admin.problem.adapter.in.web;

import com.lito.admin.problem.adapter.in.request.ProblemRequest;
import com.lito.core.admin.application.port.in.AdminProblemCommandUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/problems")
public class AdminProblemController {

    private final AdminProblemCommandUseCase adminProblemCommandUseCase;

    @PostMapping
    public void create(@RequestBody @Valid ProblemRequest problemRequest){
        adminProblemCommandUseCase.create(problemRequest.toRequestDto());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        adminProblemCommandUseCase.delete(id);
    }
}
