package com.swm.lito.core.admin.application.port.out;

import com.swm.lito.core.problem.domain.Subject;
import com.swm.lito.core.problem.domain.SubjectCategory;

import java.util.Optional;

public interface AdminProblemQueryPort {

    Optional<Subject> findSubjectById(Long id);

    Optional<SubjectCategory> findSubjectCategoryById(Long id);
}
