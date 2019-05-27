package com.cuihui.provider.providerdemo;

import com.cuihui.provider.providerdemo.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderdemoApplicationTests {

    @Resource
    private Environment env;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${user2.user}")
    private String user;
    @Value("${user2.password}")
    private String password;

    /**
     * 属性文件属性获取测试
     */
    @Test
    public void testReadSpringBootConfig() {
        //获取核心配置文件application.properties属性信息
        System.out.println("数据库连接串------>" + env.getProperty("spring.datasource.url"));
        //通过@value注解获取属性值
        System.out.println("url----------->" + url);
        //测试外部属性文件加载,类ConfigLoad.java加载外部属性文件
        System.out.println("额外的配置文件user.user-------->" + env.getProperty("user.user"));
        System.out.println("额外的配置文件user.password-------->" + env.getProperty("user.password"));
        System.out.println("额外的配置文件user2.user-------->" + user);
        System.out.println("额外的配置文件user2.password-------->" + password);

        //测试profile生效配置文件,生效配置属性spring.profiles.active
        System.out.println("dev配置文件参数------->" +env.getProperty("dev"));
        System.out.println("test配置文件参数------->" +env.getProperty("test"));
    }

    /**
     * redis工具类测试
     */
    @Test
    public void testRedisUtilDemo(){
        //redis工具类使用demo
        boolean flag = redisUtil.set("cuihui","cuihuitest",(long)30);
        System.out.println("设置key成功标识----->" + flag);
        String value = (String) redisUtil.get("cuihui");
        System.out.println("获取key结果---->" + value);
    }
}
