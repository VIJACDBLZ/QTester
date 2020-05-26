package com.vzw.apitester.controller;

import com.vzw.apitester.model.ApiBenchmark;
import com.vzw.apitester.model.RequestResult;
import com.vzw.apitester.service.ApiBenchmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiBenchmarkController {

    @Autowired
    ApiBenchmarkService apiBenchmarkService;

    @GetMapping("/benchmark")
    private List<ApiBenchmark> getAllResults() {
        return apiBenchmarkService.getAll();
    }

    @GetMapping("/benchmark/{id}")
    private Iterable<ApiBenchmark> getResult(@PathVariable("id") String benchmarkId) {
        return apiBenchmarkService.getApiBenchmarksByBenchmarkId(benchmarkId);
    }

    @DeleteMapping("/benchmark/{id}")
    private void deleteResult(@PathVariable("id") Long id) {
        apiBenchmarkService.deleteBenchmarkById(id);
    }

    @PostMapping("/result")
    private Long savePerson(@RequestBody ApiBenchmark benchmark) {
        apiBenchmarkService.saveOrUpdate(benchmark);
        return benchmark.getId();
    }
}
