package com.lito.core.admin.application.port.in;

import com.lito.core.admin.application.port.in.request.ProblemRequestDto;

public interface AdminProblemCommandUseCase {

    void create(ProblemRequestDto problemRequestDto);

    void delete(Long id);
}
