package com.cuihui.provider.providerdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication //表明当前类是SpringBoot的引导类,只会扫描当前包及其子包
@EnableCaching //开启Spring对缓存的支持
@MapperScan("com.cuihui.provider.providerdemo.dao")  //扫描mybatis下所有的mapper,可替代dao层的@Mapper注解
public class ProviderdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderdemoApplication.class, args);
    }
}