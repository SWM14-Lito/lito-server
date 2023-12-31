package com.lito.core.admin.application.service;

import com.lito.core.admin.application.port.in.AdminProblemCommandUseCase;
import com.lito.core.admin.application.port.in.request.ProblemRequestDto;
import com.lito.core.admin.application.port.out.AdminProblemCommandPort;
import com.lito.core.admin.application.port.out.AdminProblemQueryPort;
import com.lito.core.common.entity.BaseEntity;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.admin.AdminErrorCode;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.Subject;
import com.lito.core.problem.domain.SubjectCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminProblemCommandService implements AdminProblemCommandUseCase {

    private final AdminProblemCommandPort adminProblemCommandPort;
    private final AdminProblemQueryPort adminProblemQueryPort;

    @Override
    public void create(ProblemRequestDto problemRequestDto) {
        Subject subject = adminProblemQueryPort.findSubjectById(problemRequestDto.getSubjectId())
                        .orElseThrow(() -> new ApplicationException(AdminErrorCode.SUBJECT_NOT_FOUND));
        SubjectCategory subjectCategory = adminProblemQueryPort.findSubjectCategoryById(problemRequestDto.getSubjectCategoryId())
                        .orElseThrow(() -> new ApplicationException(AdminErrorCode.SUBJECT_CATEGORY_NOT_FOUND));
        Problem problem = Problem.createProblem(subject, subjectCategory, problemRequestDto.getQuestion(),
                problemRequestDto.getAnswer(), problemRequestDto.getKeyword());
        problemRequestDto.getFaqRequestDtos()
                        .forEach(faqRequestDto -> faqRequestDto.toEntity().setProblem(problem));
        adminProblemCommandPort.save(problem);
    }

    @Override
    public void delete(Long id) {
        Problem problem = adminProblemQueryPort.findProblemById(id)
                .orElseThrow(() -> new ApplicationException(AdminErrorCode.PROBLEM_NOT_FOUND));
        problem.changeStatus(BaseEntity.Status.INACTIVE);
    }
}
