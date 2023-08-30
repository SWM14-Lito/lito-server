package com.swm.lito.core.admin.application.port.in;

import com.swm.lito.core.admin.application.port.in.request.ProblemRequestDto;

public interface AdminProblemCommandUseCase {

    void create(ProblemRequestDto problemRequestDto);
}
