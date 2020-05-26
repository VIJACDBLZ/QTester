package com.vzw.apitester.service;

import com.vzw.apitester.model.ApiBenchmark;
import com.vzw.apitester.repository.ApiBenchmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiBenchmarkService {

    @Autowired
    ApiBenchmarkRepository apiBenchmarkRepository;

    public List<ApiBenchmark> getAll(){
        List<ApiBenchmark> apiBenchmarks = new ArrayList<>();
        apiBenchmarkRepository.findAll().forEach( apibenchmark -> apiBenchmarks.add(apibenchmark));
        return apiBenchmarks;
    }

    public ApiBenchmark getApiBenchmarkById(Long id) {
        return apiBenchmarkRepository.findById(id).get();
    }

    public Iterable<ApiBenchmark> getApiBenchmarksByBenchmarkId(String benchmarkId){
        return apiBenchmarkRepository.findByBenchmarkId(benchmarkId);
    }

    public void saveOrUpdate(ApiBenchmark benchmark) {
        apiBenchmarkRepository.save(benchmark);
    }

    public void deleteBenchmarkById(Long id) {
        apiBenchmarkRepository.deleteById(id);
    }
}
