package com.cuihui.consumer.consumerdemo.controller;

import com.cuihui.consumer.consumerdemo.model.User;
import com.cuihui.consumer.consumerdemo.remoteservice.IUserRemoteService;
import com.cuihui.consumer.consumerdemo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
//    RestTemplate restTemplate;
    private IUserRemoteService userRemoteService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/remote-findAll")
    public List<Object> remoteQueryList(){
        logger.info("远程服务接口调用开始");
        // 服务提供者url、返回数据类型
//        return restTemplate.getForObject("http://providerdemo/user/findAll", List.class);
        return userRemoteService.queryList();
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<User> queryList(){
        logger.info("本服务接口调用开始");
        return userService.queryList();
    }

}