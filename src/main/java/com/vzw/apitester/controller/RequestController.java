package com.vzw.apitester.controller;

import com.vzw.apitester.model.ApiTesterConfig;
import com.vzw.apitester.model.RequestConfig;
import com.vzw.apitester.service.ApiTesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RequestController {

    @Autowired
    ApiTesterConfig apiTesterConfig;

    @Autowired
    ApiTesterService apiTesterService;

    @GetMapping("/requestConfigs")
    public @ResponseBody Map<String , Object> getRequestDetails(){

        Map<String , Object> responseMap = new HashMap<>();

        responseMap.put("requestConfigs", apiTesterConfig.getRequestConfigs());
        responseMap.put("success", true);

        return responseMap;
    }

    @PostMapping("/benchmark")
    public @ResponseBody Map<String , Object> benchmarkEndpoints(@RequestBody Map<String , Object> requestMap){
        Map<String , Object> responseMap = new HashMap<>();
        boolean success = false;
        try{
            responseMap = apiTesterService.doBenchmark(requestMap);
            success = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            responseMap.put("success", success);
        }

        return responseMap;



    }


}
