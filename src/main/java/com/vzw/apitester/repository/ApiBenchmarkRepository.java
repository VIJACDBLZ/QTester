package com.vzw.apitester.repository;

import com.vzw.apitester.model.ApiBenchmark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiBenchmarkRepository extends CrudRepository<ApiBenchmark, Long> {
    Iterable<ApiBenchmark> findByBenchmarkId(String benchmarkId);
}
