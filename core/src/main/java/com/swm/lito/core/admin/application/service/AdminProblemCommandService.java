package com.swm.lito.core.admin.application.service;

import com.swm.lito.core.admin.application.port.in.AdminProblemCommandUseCase;
import com.swm.lito.core.admin.application.port.in.request.FaqRequestDto;
import com.swm.lito.core.admin.application.port.in.request.PostRequestDto;
import com.swm.lito.core.admin.application.port.out.AdminProblemCommandPort;
import com.swm.lito.core.common.exception.ApplicationException;
import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.problem.domain.Subject;
import com.swm.lito.core.problem.domain.SubjectCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.swm.lito.core.common.exception.admin.AdminErrorCode.SUBJECT_CATEGORY_NOT_FOUND;
import static com.swm.lito.core.common.exception.admin.AdminErrorCode.SUBJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminProblemCommandService implements AdminProblemCommandUseCase {

    private final AdminProblemCommandPort adminProblemCommandPort;

    @Override
    public void create(PostRequestDto postRequestDto) {
        Subject subject = adminProblemCommandPort.findSubjectById(postRequestDto.getSubjectId())
                        .orElseThrow(() -> new ApplicationException(SUBJECT_NOT_FOUND));
        SubjectCategory subjectCategory = adminProblemCommandPort.findSubjectCategoryById(postRequestDto.getSubjectCategoryId())
                        .orElseThrow(() -> new ApplicationException(SUBJECT_CATEGORY_NOT_FOUND));
        Problem problem = Problem.createProblem(subject, subjectCategory, postRequestDto.getQuestion(),
                postRequestDto.getAnswer(), postRequestDto.getKeyword(),
                postRequestDto.getFaqRequestDtos().stream()
                        .map(FaqRequestDto::toEntity)
                        .collect(Collectors.toList()));
        adminProblemCommandPort.save(problem);
    }
}
