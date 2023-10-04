package com.lito.core.problem.application.port.in;

import com.lito.core.problem.domain.Batch;

import java.time.LocalDate;

public interface BatchQueryUseCase {

    Batch findBatch(LocalDate requestDate);
}
