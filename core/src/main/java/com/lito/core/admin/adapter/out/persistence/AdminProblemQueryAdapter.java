package com.lito.core.admin.adapter.out.persistence;

import com.lito.core.admin.application.port.out.AdminProblemQueryPort;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.Subject;
import com.lito.core.problem.domain.SubjectCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminProblemQueryAdapter implements AdminProblemQueryPort {

    private final AdminSubjectRepository adminSubjectRepository;
    private final AdminSubjectCategoryRepository adminSubjectCategoryRepository;
    private final AdminProblemRepository adminProblemRepository;

    @Override
    public Optional<Subject> findSubjectById(Long id) {
        return adminSubjectRepository.findById(id);
    }

    @Override
    public Optional<SubjectCategory> findSubjectCategoryById(Long id) {
        return adminSubjectCategoryRepository.findById(id);
    }

    @Override
    public Optional<Problem> findProblemById(Long id) {
        return adminProblemRepository.findById(id);
    }
}
