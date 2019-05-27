package com.cuihui.provider.providerdemo.utils.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.util.List;

/**
 * 订阅过期消息通知,重写CommandLineRunner接口的run方法,实现服务启动时调用该方法
 */
@Component //服务启动时需要实例化该bean,以调用run方法
@Profile("!test")  //test为spring.profiles.active配置值，表示不实例化该类(加载该类junit测试用例无法执行,需要时再打开)
public class SubExpireMessage implements CommandLineRunner {

    @Autowired
    private PubSubListener pubSubListener;

    @Override
    public void run(String... strings) throws Exception {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

        Jedis jedis = pool.getResource();
        jedis.auth("since2012");
        config(jedis);
        //订阅消息通知
//        jedis.psubscribe(new PubSubListener(), "__key*__:*");  //模糊匹配
        jedis.psubscribe(pubSubListener, "__keyevent@0__:expired");
    }

    /**
     * 启用redis通知(判断配置文件是否启用通知)
     * @param jedis
     */
    private static void config(Jedis jedis) {
        String parameter = "notify-keyspace-events";
        List<String> notify = jedis.configGet(parameter);
        if (notify.get(1).equals("")) {
            jedis.configSet(parameter, "Ex"); //过期事件
        }
    }
}