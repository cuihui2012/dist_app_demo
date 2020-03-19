package com.cuihui.provider.providerdemo.controller;

import com.cuihui.provider.providerdemo.model.User;
import com.cuihui.provider.providerdemo.service.IUserService;
import com.cuihui.provider.providerdemo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<User> queryList(){
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
}