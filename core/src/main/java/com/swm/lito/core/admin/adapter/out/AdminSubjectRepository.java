package com.swm.lito.core.admin.adapter.out;

import com.swm.lito.core.problem.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSubjectRepository extends JpaRepository<Subject, Long> {
}
