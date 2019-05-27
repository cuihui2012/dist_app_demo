package com.cuihui.provider.providerdemo.utils.pubsub;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

/**
 * 发布/订阅监听类
 */
@Component
public class PubSubListener extends JedisPubSub {

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("监听方法：onPSubscribe");
        System.out.println("pattern----->" + pattern);
        System.out.println("subscribedChannels----->" + subscribedChannels);
        System.out.println("--------------------------------");
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

        System.out.println("监听方法：onPMessage");
        System.out.println("pattern----->" + pattern);
        System.out.println("channel----->" + channel);
        System.out.println("message----->" + message);
        System.out.println("--------------------------------");
    }
}