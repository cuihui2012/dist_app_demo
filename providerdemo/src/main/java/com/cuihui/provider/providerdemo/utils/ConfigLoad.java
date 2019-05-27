package com.cuihui.provider.providerdemo.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration  //内部有Component注解,Spring会实例化该bean
@PropertySource(value = {"classpath:user.properties","classpath:user2.properties"})  //加载指定属性文件(value为数组)
public class ConfigLoad {
    /**
     * 该类无具体业务,只加载额外的properties文件
     */
}