package com.lito.core.admin.application.port.out;

import com.lito.core.problem.domain.Problem;

public interface AdminProblemCommandPort {

    void save(Problem problem);
}
