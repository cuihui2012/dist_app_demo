package com.cuihui.provider.providerdemo.utils;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * company 正元智慧
 *
 * @author cuihui
 * Jedis单工具类,未使用SpringBoot框架
 */
public final class JedisSingleUtil {


    private static final Logger logger = Logger.getLogger(JedisSingleUtil.class);
    private static final Gson gson = new Gson();
    private static final String redisIp = "localhost";

    /**
     * 获取jedis连接
     *
     * @return
     */
    public static Jedis getJedisConn() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        Jedis jedis = pool.getResource();
        jedis.auth("since2012");
        return jedis;
    }

    /**
     * set String 数据
     *
     * @param key
     * @param val
     */
    public static void set(String key, String val) {
        Jedis jedis = getJedisConn();
        try {
            jedis.set(key, val == null ? "" : val);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            jedis.close();
        }
    }

    /**
     * 设置key并设置过期时间
     * @param key
     * @param val
     * @param seconds
     */
    public static void set(String key, String val, int seconds) {
        Jedis jedis = getJedisConn();
        try {
            jedis.setex(key,seconds, val == null ? "" : val);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            jedis.close();
        }
    }

    /**
     * get 取key值对应的数据
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = getJedisConn();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 设置hash类型值
     *
     * @param key
     * @param field
     * @param value
     */
    public static void hset(String key, String field, String value) {
        Jedis jedis = getJedisConn();
        try {
            jedis.hset(key, field, value == null ? "" : value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            jedis.close();
        }
    }

    public static String hget(String key, String field) {
        Jedis jedis = getJedisConn();
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            jedis.close();
        }
    }

    public static boolean exists(String key) {
        Jedis jedis = getJedisConn();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String args[]) {
        JedisSingleUtil.set("cuihui","ccccccccccccccccccccccccccc",10);
    }
}