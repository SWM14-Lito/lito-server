package com.swm.lito.core.admin.application.port.out;

import com.swm.lito.core.problem.domain.Problem;

public interface AdminProblemCommandPort {

    void save(Problem problem);
}
