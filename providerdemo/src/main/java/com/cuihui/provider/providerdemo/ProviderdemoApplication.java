package com.cuihui.provider.providerdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication //表明当前类是SpringBoot的引导类,只会扫描当前包及其子包
@EnableDiscoveryClient //开启发现服务功能
@EnableFeignClients  //使用feign简化微服务调用
@EnableCaching //开启Spring对缓存的支持
@MapperScan("com.cuihui.provider.providerdemo.dao")  //扫描mybatis下所有的mapper,可替代dao层的@Mapper注解
public class ProviderdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderdemoApplication.class, args);
    }
    @LoadBalanced //使用负载均衡机制
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}