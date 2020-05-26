package com.vzw.apitester.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ApiBenchmark {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String benchmarkId;

    private String requestConfigId;

    @Column(columnDefinition = "TEXT")
    private String request;

    private int firstApiLatency;

    private String firstApiUrl;

    @Column(columnDefinition = "TEXT")
    private String firstApiResponse;

    private int secondApiLatency;

    private String secondApiUrl;

    @Column(columnDefinition = "TEXT")
    private String secondApiResponse;

    private boolean isResponseIdentical;

    public ApiBenchmark(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBenchmarkId() {
        return benchmarkId;
    }

    public void setBenchmarkId(String benchmarkId) {
        this.benchmarkId = benchmarkId;
    }

    public String getRequestConfigId() {
        return requestConfigId;
    }

    public void setRequestConfigId(String requestConfigId) {
        this.requestConfigId = requestConfigId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getFirstApiLatency() {
        return firstApiLatency;
    }

    public void setFirstApiLatency(int firstApiLatency) {
        this.firstApiLatency = firstApiLatency;
    }

    public String getFirstApiUrl() {
        return firstApiUrl;
    }

    public void setFirstApiUrl(String firstApiUrl) {
        this.firstApiUrl = firstApiUrl;
    }

    public String getFirstApiResponse() {
        return firstApiResponse;
    }

    public void setFirstApiResponse(String firstApiResponse) {
        this.firstApiResponse = firstApiResponse;
    }

    public int getSecondApiLatency() {
        return secondApiLatency;
    }

    public void setSecondApiLatency(int secondApiLatency) {
        this.secondApiLatency = secondApiLatency;
    }

    public String getSecondApiUrl() {
        return secondApiUrl;
    }

    public void setSecondApiUrl(String secondApiUrl) {
        this.secondApiUrl = secondApiUrl;
    }

    public String getSecondApiResponse() {
        return secondApiResponse;
    }

    public void setSecondApiResponse(String secondApiResponse) {
        this.secondApiResponse = secondApiResponse;
    }

    public boolean isResponseIdentical() {
        return isResponseIdentical;
    }

    public void setResponseIdentical(boolean responseIdentical) {
        isResponseIdentical = responseIdentical;
    }

    public ApiBenchmark(RequestResult firstApiResult , RequestResult secondApiResult , boolean hasIdenticalResponse){

        if(Objects.isNull(firstApiResult) || Objects.isNull(secondApiResult))
            throw new IllegalArgumentException("Invalid argument passed for ApiBenchmark!");

        this.benchmarkId = firstApiResult.getBenchmarkId();
        this.requestConfigId = firstApiResult.getRequestConfigId();
        this.request = firstApiResult.getRequest();
        this.firstApiUrl = firstApiResult.getRequestUrl();
        this.firstApiLatency = firstApiResult.getLatency();
        this.firstApiResponse = firstApiResult.getResponse();
        this.secondApiUrl = secondApiResult.getRequestUrl();
        this.secondApiLatency = secondApiResult.getLatency();
        this.secondApiResponse = secondApiResult.getResponse();

        //TODO:Later use XMLUnit to compare the responses
        this.isResponseIdentical = hasIdenticalResponse;
    }
}
