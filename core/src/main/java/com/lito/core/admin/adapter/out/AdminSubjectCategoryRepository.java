package com.lito.core.admin.adapter.out;

import com.lito.core.problem.domain.SubjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSubjectCategoryRepository extends JpaRepository<SubjectCategory, Long> {
}
