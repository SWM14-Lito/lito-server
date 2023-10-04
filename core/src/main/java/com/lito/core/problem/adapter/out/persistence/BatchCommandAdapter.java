package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.application.port.out.BatchCommandPort;
import com.lito.core.problem.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BatchCommandAdapter implements BatchCommandPort {

    private final BatchRepository batchRepository;


    @Override
    public void save(Batch batch) {
        batchRepository.save(batch);
    }
}
