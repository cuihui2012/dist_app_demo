package com.cuihui.config.configdemo.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/config")
public class RefreshController {

    /*
    * 将配置服务端refresh接口重新包装下,在github上的Webhooks上触发
    * 配置时,localhost:8080需做一次内网穿透
    * 该接口刷新时依赖注解@RefreshScope,对代码侵入性较强,需提前预知要刷新的参数
    * */
    @RequestMapping(value = "/refresh")
    public String refresh(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8888/bus/refresh", request, String.class);

        return "bus-refresh";
    }
}