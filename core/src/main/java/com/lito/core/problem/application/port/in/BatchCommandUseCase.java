package com.lito.core.problem.application.port.in;

import com.lito.core.problem.domain.Batch;

public interface BatchCommandUseCase {

    void save(Batch batch);
}
