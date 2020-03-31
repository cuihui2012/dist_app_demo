package com.cuihui.consumer.consumerdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableDiscoveryClient //开启发现服务功能
@EnableFeignClients  //使用feign简化微服务调用
@MapperScan("com.cuihui.consumer.consumerdemo.dao")  //扫描mybatis下所有的mapper,可替代dao层的@Mapper注解
public class ConsumerdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerdemoApplication.class, args);
    }

    @LoadBalanced //使用负载均衡机制
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
