package com.vzw.apitester.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RequestConfig {

    @JsonProperty("id")
    private String id;
    @JsonProperty("request-title")
    private String requestTitle;
    @JsonProperty("first-endpoint")
    private String firstEndpoint;
    @JsonProperty("second-endpoint")
    private String secondEndpoint;
    @JsonProperty("request-method")
    private String requestMethod;
    @JsonProperty("request-template")
    private String requestTemplate;


    public RequestConfig(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getFirstEndpoint() {
        return firstEndpoint;
    }

    public void setFirstEndpoint(String firstEndpoint) {
        this.firstEndpoint = firstEndpoint;
    }

    public String getSecondEndpoint() {
        return secondEndpoint;
    }

    public void setSecondEndpoint(String secondEndpoint) {
        this.secondEndpoint = secondEndpoint;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestTemplate() {
        return requestTemplate;
    }

    public void setRequestTemplate(String requestTemplate) {
        this.requestTemplate = requestTemplate;
    }
}
