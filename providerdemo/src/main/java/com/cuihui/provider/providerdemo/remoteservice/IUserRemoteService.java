package com.cuihui.provider.providerdemo.remoteservice;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//value为服务名称,fallback为容错机制,降级处理
@FeignClient(value = "consumerdemo", fallback = UserRemoteServiceFallback.class)
public interface IUserRemoteService {
    @RequestMapping(value = "/consumer/findAll", method = RequestMethod.GET)
    List<Object> queryList();
}
