package com.lito.core.problem.application.service;

import com.lito.core.problem.application.port.in.BatchCommandUseCase;
import com.lito.core.problem.application.port.out.BatchCommandPort;
import com.lito.core.problem.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchCommandService implements BatchCommandUseCase {

    private final BatchCommandPort batchCommandPort;

    @Override
    public void save(Batch batch) {
        batchCommandPort.save(batch);
    }
}
