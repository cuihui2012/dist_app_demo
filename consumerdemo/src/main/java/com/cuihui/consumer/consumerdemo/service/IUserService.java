package com.cuihui.consumer.consumerdemo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

//value为服务名称,fallback为容错机制,降级处理
@FeignClient(value = "providerdemo", fallback = UserServiceFallback.class)
public interface IUserService {
    @RequestMapping(value = "/user/findAll", method = RequestMethod.GET)
    List<Object> queryList();
}
