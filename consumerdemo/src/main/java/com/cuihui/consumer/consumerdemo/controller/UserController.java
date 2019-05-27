package com.cuihui.consumer.consumerdemo.controller;

import com.cuihui.consumer.consumerdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/user-findAll")
    public List<User> queryList(){
        // 服务提供者url、返回数据类型
        return restTemplate.getForObject("http://providerdemo/user/findAll", List.class);
    }

}