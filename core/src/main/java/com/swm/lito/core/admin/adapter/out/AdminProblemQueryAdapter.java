package com.swm.lito.core.admin.adapter.out;

import com.swm.lito.core.admin.application.port.out.AdminProblemQueryPort;
import com.swm.lito.core.problem.domain.Subject;
import com.swm.lito.core.problem.domain.SubjectCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminProblemQueryAdapter implements AdminProblemQueryPort {

    private final AdminSubjectRepository adminSubjectRepository;
    private final AdminSubjectCategoryRepository adminSubjectCategoryRepository;

    @Override
    public Optional<Subject> findSubjectById(Long id) {
        return adminSubjectRepository.findById(id);
    }

    @Override
    public Optional<SubjectCategory> findSubjectCategoryById(Long id) {
        return adminSubjectCategoryRepository.findById(id);
    }
}
