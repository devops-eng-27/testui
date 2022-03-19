package com.cloud.mqweb.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service("apiService")
@Slf4j
public class ApiServiceImpl implements ApiService{

    private RestTemplate restTemplate = new RestTemplate();

    // VM 생성 요청 API
    public String createApi(String userId, String userPw, String name, String location) {

        // 생성에 필요한 Parameter 추가
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId",userId);
        param.add("userPw",userPw);
        param.add("name",name);
        param.add("location",location);
        param.add("cpu", "701b6739-48a8-4541-8c82-8c52dc95400d");
        param.add("ip", "0cc87cc7-cf47-421f-9a8b-20bc0611bcda");

        log.info(String.valueOf(param));

        String uri = "http://localhost:8000/mqsvr/create";

        // restTemplate로 api 전달
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, param, String.class);

        log.info("status code : {}", responseEntity.getStatusCode());
        log.info("body : {}", responseEntity.getBody());

        String vmGuestIp = responseEntity.getBody();

        return vmGuestIp;
    }

    // VM 삭제 요청
    public String deleteApi(String userId, String userPw, String vmGuestIp) {

        // 삭제에 필요한 Parameter 추가
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId",userId);
        param.add("userPw",userPw);
        param.add("vmGuestIp",vmGuestIp);

        log.info(String.valueOf(param));

        String uri = "http://localhost:8000/net/deleteip";

        // restTemplate로 api 전달
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, param, String.class);

        log.info("status code : {}", responseEntity.getStatusCode());
        log.info("body : {}", responseEntity.getBody());

        return "";
    }

    // Agent control 요청
    public String agentApi(String URI, String kafka, String zookeeper, String kafdrop, String control) {

        // control에 필요한 Parameter 추가
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("kafka",kafka);
        param.add("zookeeper",zookeeper);
        param.add("kafdrop",kafdrop);
        param.add("control",control);

        log.info(String.valueOf(param));

        // restTemplate로 api 전달
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI, param, String.class);

        log.info("status code : {}", responseEntity.getStatusCode());
        log.info("body : {}", responseEntity.getBody());

        return "";
    }


}
