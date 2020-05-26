package com.vzw.apitester.model;

public class RequestResult {

    private int latency;

    private String requestUrl;

    private int sequenceId;

    private String request;

    private String response;

    private String benchmarkId;

    private String requestConfigId;

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequest() {
        return request;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
}
