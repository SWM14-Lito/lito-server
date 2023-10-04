package com.lito.core.problem.application.port.out;

import com.lito.core.problem.domain.Batch;

public interface BatchCommandPort {

    void save(Batch batch);
}
