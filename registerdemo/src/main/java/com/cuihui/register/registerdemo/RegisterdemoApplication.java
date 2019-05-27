package com.cuihui.register.registerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication //表明当前类是SpringBoot的引导类,只会扫描当前包及其子包
@EnableEurekaServer  //服务注册中心提供服务注册功能
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) //忽略默认数据源配置信息加载
public class RegisterdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterdemoApplication.class, args);
    }
}