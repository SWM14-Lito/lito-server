package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.application.port.out.BatchQueryPort;
import com.lito.core.problem.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BatchQueryAdapter implements BatchQueryPort {

    private final BatchRepository batchRepository;

    @Override
    public Optional<Batch> findBatch(LocalDate requestDate) {
        return batchRepository.findTopByRequestDateOrderByCreatedAtDesc(requestDate);
    }
}
