package com.cuihui.provider.providerdemo.controller;

import com.cuihui.provider.providerdemo.model.User;
import com.cuihui.provider.providerdemo.remoteservice.IUserRemoteService;
import com.cuihui.provider.providerdemo.service.IUserService;
import com.cuihui.provider.providerdemo.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provider")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserRemoteService userRemoteService;

    @RequestMapping(value = "/remote-findAll")
    public List<Object> remoteQueryList(){
        logger.info("远程服务接口调用开始");
        return userRemoteService.queryList();
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<User> queryList(){
        logger.info("本服务接口调用开始");
        ////////////////////////////////测试key过期是否被监听到///////////////////////////////
        //redis工具类使用demo
//        boolean flag = redisUtil.set("cuihui","cuihuitest",(long)5);
//        System.out.println("设置key成功标识----->" + flag);
//        String value = (String) redisUtil.get("cuihui");
//        System.out.println("获取key结果---->" + value);
        /////////////////////////////////////////////////////////////////////////////////////
        return userService.queryList();
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(@RequestBody Map<String,String> paramMap){
        return userService.addUser(paramMap);
    }


    @Value("${user2.user}")
    private String user;
    @Value("${user2.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${dev}")
    private String dev;
    @Value("${test}")
    private String test;
    @Value("${node.ip}")
    private String nodeIP;

    @RequestMapping(value = "/getConfigValue", method = RequestMethod.GET)
    public String getConfigValue(){
        System.out.println("user------>" + user);
        System.out.println("password------>" + password);
        System.out.println("datasourceUrl------>" + datasourceUrl);
        System.out.println("dev------>" + dev);
        System.out.println("test------>" + test);
        System.out.println("nodeIP------>" + nodeIP);
        return null;
    }
}