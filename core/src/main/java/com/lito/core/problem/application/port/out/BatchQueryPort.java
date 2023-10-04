package com.lito.core.problem.application.port.out;

import com.lito.core.problem.domain.Batch;

import java.time.LocalDate;
import java.util.Optional;

public interface BatchQueryPort {

    Optional<Batch> findBatch(LocalDate requestDate);
}
