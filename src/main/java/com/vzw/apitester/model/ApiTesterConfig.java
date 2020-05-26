package com.vzw.apitester.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiTesterConfig {

    private static Logger log = LogManager.getLogger(ApiTesterConfig.class.getName());

    private List<RequestConfig> requestConfigs = new ArrayList<>();


    @PostConstruct
    public void loadConfiguration(){
        String jsonConfig;
        try {
            InputStream is = getResourceFileAsInputStream("config.json");
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                jsonConfig = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            } else {
                throw new RuntimeException("config.json not found");
            }

            requestConfigs = new ObjectMapper().readValue(jsonConfig, new TypeReference<List<RequestConfig>>() {});

        } catch (JsonProcessingException e) {
            log.error("Unable to load the configuration!");
            e.printStackTrace();
        }

    }

    public List<RequestConfig> getRequestConfigs() {
        return requestConfigs;
    }

    public void setRequestConfigs(List<RequestConfig> requestConfigs) {
        this.requestConfigs = requestConfigs;
    }

    public static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = ApiTesterConfig.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}
