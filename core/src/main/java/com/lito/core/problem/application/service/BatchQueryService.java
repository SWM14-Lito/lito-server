package com.lito.core.problem.application.service;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.batch.BatchErrorCode;
import com.lito.core.problem.application.port.in.BatchQueryUseCase;
import com.lito.core.problem.application.port.out.BatchQueryPort;
import com.lito.core.problem.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BatchQueryService implements BatchQueryUseCase {

    private final BatchQueryPort batchQueryPort;


    @Override
    public Batch findBatch(LocalDate requestDate) {
        return batchQueryPort.findBatch(requestDate)
                .orElseThrow(() -> new ApplicationException(BatchErrorCode.BATCH_NOT_FOUND));
    }
}
