package com.lito.core.admin.adapter.out;

import com.lito.core.problem.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSubjectRepository extends JpaRepository<Subject, Long> {
}
