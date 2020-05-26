package com.vzw.apitester.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzw.apitester.model.ApiBenchmark;
import com.vzw.apitester.model.RequestResult;
import com.vzw.apitester.repository.ApiBenchmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;

@Component
public class ApiTesterService {

    @Value("${threads.per.endpoint:50}")
    private int threadPerEndpoint;

    @Autowired
    ApiBenchmarkRepository apiBenchmarkRepository;

    public Map<String , Object> doBenchmark(Map<String , Object> dataMap) throws InterruptedException, ExecutionException, JsonProcessingException {

        String benchmarkId = UUID.randomUUID().toString();
        List<ApiBenchmark> benchmarkList = new ArrayList<>();

        int sequenceId = 0;

        List<List<String>> paramFieldsList = (List<List<String>>) dataMap.get("placeholderfields");
        Map<String , Object> benchmarkResult = new HashMap<>();

        String requestTemplate = (String) dataMap.get("requesttemplate");
        String firstendpoint = (String) dataMap.get("firstendpoint");
        String secondendpoint = (String) dataMap.get("secondendpoint");
        String requestConfigId = (String) dataMap.get("requestconfigid");
        //String requestPayload = null;


        ExecutorService poolForFirstEndpoint = Executors.newFixedThreadPool(threadPerEndpoint);
        ExecutorService poolForSecondEndpoint = Executors.newFixedThreadPool(threadPerEndpoint);

        List<Callable<RequestResult>> requestsForFirstEndpoint = new ArrayList<>();
        List<Callable<RequestResult>> requestsForSecondEndpoint = new ArrayList<>();

        Map<Integer, RequestResult> resultsForFirstEndpoint = new HashMap<>();
        Map<Integer, RequestResult> resultsForSecondEndpoint = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //TODO:set this based on config to allow both JSON/XML requests
        headers.setContentType(MediaType.APPLICATION_XML);



        for(List<String> paramFields : paramFieldsList){

            //preventing local variables referenced from an inner class must be final or effectively final
            int internalSequenceId = sequenceId;
            String requestPayload = setFieldsInTemplateRequest(requestTemplate , paramFields);
            HttpEntity<String> request = new HttpEntity<String>(requestPayload, headers);


            requestsForFirstEndpoint.add(new Callable<RequestResult>() {
                public RequestResult call() {
                    StopWatch stopwatch = new StopWatch("firstendpoint");
                    stopwatch.start();
                    ResponseEntity<String> response = restTemplate.postForEntity(firstendpoint, request, String.class);
                    stopwatch.stop();

                    RequestResult result = new RequestResult();
                    result.setSequenceId(internalSequenceId);
                    result.setBenchmarkId(benchmarkId);
                    result.setRequestConfigId(requestConfigId);
                    result.setRequestUrl(firstendpoint);
                    result.setRequest(requestPayload);
                    result.setResponse(response.getBody());
                    result.setLatency((int) stopwatch.getTotalTimeMillis());

                    return result;

                }
            });

            requestsForSecondEndpoint.add(new Callable<RequestResult>() {
                public RequestResult call() {
                    StopWatch stopwatch = new StopWatch("secondendpoint");
                    stopwatch.start();
                    ResponseEntity<String> response = restTemplate.postForEntity(secondendpoint, request, String.class);
                    stopwatch.stop();

                    RequestResult result = new RequestResult();
                    result.setSequenceId(internalSequenceId);
                    result.setBenchmarkId(benchmarkId);
                    result.setRequestConfigId(requestConfigId);
                    result.setRequestUrl(secondendpoint);
                    result.setRequest(requestPayload);
                    result.setResponse(response.getBody());
                    result.setLatency((int) stopwatch.getTotalTimeMillis());

                    return result;

                }
            });

            sequenceId++;
        }

        List<Future<RequestResult>> resultsForFirstEndpointFuture = poolForFirstEndpoint.invokeAll(requestsForFirstEndpoint);
        List<Future<RequestResult>> resultsForSecondEndpointFuture = poolForSecondEndpoint.invokeAll(requestsForSecondEndpoint);

        for (Future<RequestResult> future : resultsForFirstEndpointFuture)
            resultsForFirstEndpoint.put(future.get().getSequenceId(), future.get());

        for (Future<RequestResult> future : resultsForSecondEndpointFuture)
            resultsForSecondEndpoint.put(future.get().getSequenceId(), future.get());

        poolForFirstEndpoint.shutdown();
        poolForFirstEndpoint.shutdown();

        //combining respective requests from both the lists
        long totLatencyForFirstEndpoint = 0l;
        long totLatencyForSecondEndpoint = 0l;
        boolean hasIdenticalResponsesForAllRequests = true;
        for(int i=0; i < sequenceId; i++){

            totLatencyForFirstEndpoint = totLatencyForFirstEndpoint + resultsForFirstEndpoint.get(i).getLatency();
            totLatencyForSecondEndpoint = totLatencyForSecondEndpoint + resultsForSecondEndpoint.get(i).getLatency();

            boolean hasIdenticalResponse;

            if(resultsForFirstEndpoint.get(i).getResponse().equals(resultsForSecondEndpoint.get(i).getResponse()))
                hasIdenticalResponse = true;
            else{
                hasIdenticalResponse = false;
                hasIdenticalResponsesForAllRequests = false;
            }


            ApiBenchmark benchmark = new ApiBenchmark(resultsForFirstEndpoint.get(i) , resultsForSecondEndpoint.get(i) , hasIdenticalResponse);
            benchmarkList.add(benchmark);

        }

        apiBenchmarkRepository.saveAll(benchmarkList);

        System.out.println(totLatencyForFirstEndpoint);
        System.out.println(totLatencyForSecondEndpoint);

        benchmarkResult.put("benchmark", benchmarkList);
        benchmarkResult.put("benchmarkId", benchmarkId);
        benchmarkResult.put("avgLatencyForFirstEndpoint", totLatencyForFirstEndpoint/sequenceId -1);
        benchmarkResult.put("avgLatencyForSecondEndpoint", totLatencyForSecondEndpoint/sequenceId -1);
        benchmarkResult.put("allResponseAreIdentical", hasIdenticalResponsesForAllRequests);

        return benchmarkResult;
    }


    private String setFieldsInTemplateRequest(String requestTemplate , List<String> placeholderFields){

        //TODO: Replace placeholders in request template with paramFields
        return requestTemplate;
    }

}
