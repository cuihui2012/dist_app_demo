package com.cuihui.provider.providerdemo.utils;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import redis.clients.jedis.*;

import java.util.*;

/**
 * redis缓存集群工具
 *
 * @author cui hui
 * @version 1.0
 */
public final class JedisClusterUtil {

    private static final Logger log = Logger.getLogger(JedisClusterUtil.class);
    private static final Gson gson = new Gson();
    private static JedisCluster jedisCluster;

    static {
        try {
            String redisClusterNodes = "139.155.4.128:7000,139.155.4.128:7001,139.155.4.128:7002,139.155.4.128:7003,139.155.4.128:7004,139.155.4.128:7005";
            String password = "since2012";
            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            for (String redisNode : redisClusterNodes.split(",")) {
                String[] hostAndPort = redisNode.split(":");
                if (hostAndPort != null && hostAndPort.length == 2) {
                    jedisClusterNodes.add(new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
                }
            }

            if (jedisClusterNodes.size() > 0) {
                // config全部采取默认
                jedisCluster = new JedisCluster(jedisClusterNodes, 30000, 30000, 5, password, new JedisPoolConfig());
            }

        } catch (Exception e) {
            log.error("jedisCluster create error!", e);
            System.exit(1);
        }
    }


    public static boolean hexists(String key, String field) {
        try {
            return jedisCluster.hexists(key, field);
        } catch (Exception e) {
            log.info("", e);
            return false;
        }
    }

    /**
     * 以秒为单位返回key的剩余存活时间
     *
     * @param key
     * @return
     */
    public static Long ttl(String key) {
        try {
            return jedisCluster.ttl(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 指定时间失效
     *
     * @param key
     * @param unixTime unix时间戳，java获取的时间戳是毫秒 需要除以1000
     * @return
     */
    public static Long expireAt(String key, long unixTime) {
        try {
            return jedisCluster.expireAt(key, unixTime);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static String set(String key, Object obj) {
        try {
            if (obj instanceof String) {
                return jedisCluster.set(key, obj == null ? "" : ((String) obj));
            } else {
                return jedisCluster.set(key, gson.toJson(obj));
            }
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static void set(String key, Object obj, Integer minute) {
        try {
            if (obj instanceof String) {
                jedisCluster.setex(key, 60 * minute, obj == null ? "" : ((String) obj));
            } else {
                jedisCluster.setex(key, 60 * minute, gson.toJson(obj));
            }
        } catch (Exception e) {
            log.info("", e);
        }
    }


    /**
     * 删除key 对应的数据
     *
     * @param key
     */
    public static Long delete(String key) {
        try {
            return jedisCluster.del(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static void delete(String... keys) {
        for (String key : keys) {
            jedisCluster.del(key);
        }
    }

    /**
     * 递减(数据为整型)
     *
     * @param key
     * @return
     */
    public static Long decr(String key) {
        try {
            return jedisCluster.decr(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * 减number值返回值(数据为整型)
     *
     * @param key
     * @param number
     * @return
     */
    public static Long decr(String key, int number) {
        try {
            return jedisCluster.decrBy(key, number);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * 设置失效时间key 对应的数据
     *
     * @param key
     * @param sec
     * @return
     */
    public static Long expire(String key, int sec) {
        try {
            return jedisCluster.expire(key, sec);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * 递增(数据为整型)
     *
     * @param key
     * @return
     */
    public static Long incr(String key) {
        try {
            return jedisCluster.incr(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * 增number值返回值(数据为整型)
     *
     * @param key
     * @param number
     * @return
     */
    public static Long incr(String key, int number) {
        try {
            return jedisCluster.incrBy(key, number);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * get 取key值对应的数据
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        try {
            return jedisCluster.get(key);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public static void setStringSec(String key, String val, int second) {
        try {
            jedisCluster.setex(key, second, val == null ? "" : val);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    /**
     * 查询是否存在key 对应的数据
     *
     * @param key
     * @return
     */
    public static Boolean exists(String key) {
        try {
            return jedisCluster.exists(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * 在指定Key所关联的List Value的头部插入参数中给出的所有Values。
     * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入。
     * 如果该键的Value不是链表类型，该命令将返回相关的错误信息。
     *
     * @param key
     * @param value
     * @return 插入后链表中元素的数量。
     */
    public static Long lpush(String key, String value) {
        try {
            return jedisCluster.lpush(key, value);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }


    /**
     * 在指定Key所关联的List Value的尾部插入参数中给出的所有Values。
     * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入。
     * 如果该键的Value不是链表类型，该命令将返回相关的错误信息。
     *
     * @param key
     * @param value
     * @return 插入后链表中元素的数量。
     */
    public static Long rpush(String key, String value) {
        try {
            return jedisCluster.rpush(key, value);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * 返回并弹出指定Key关联的链表中的最后，即尾部元素，。如果该Key不存，返回nil。
     *
     * @param key
     * @return
     */
    public static String rpop(String key) {
        try {
            return jedisCluster.rpop(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素，。如果该Key不存，返回nil。
     *
     * @param key
     * @return
     */
    public static String lpop(String key) {
        try {
            return jedisCluster.lpop(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * @param key
     * @param field
     * @param value
     */
    public static void hset(String key, String field, String value) {
        try {
            jedisCluster.hset(key, field, value == null ? "" : value);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    /**
     * hash自增 在名字为key的map里面为field自增step
     *
     * @param key   map的名字
     * @param field 字段
     * @param step  自增值
     * @return 自增后的值
     */
    public static Long hincrBy(String key, String field, long step) {
        try {
            return jedisCluster.hincrBy(key, field, step);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }


    /**
     * 命令用于设置指定字段各自的值，在存储于键的散列。此命令将覆盖哈希任何现有字段。如果键不存在，新的key由哈希创建。
     *
     * @param key
     * @param hash
     */
    public static void hmset(String key, Map<String, String> hash) {
        try {
            jedisCluster.hmset(key, hash);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    public static String hget(String key, String field) {
        try {
            return jedisCluster.hget(key, field);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static Map<String, String> hgetAll(String key) {
        try {
            return jedisCluster.hgetAll(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <li>@Description:获取一个list
     * <li>@param key
     * <li>@param start 开始 从0开始
     * <li>@param stop 结束 正数表示第N个 负数表示倒数第N个
     * <li>@return
     * <li>创建人：韩啸
     * <li>创建时间：2016年8月10日
     * <li>修改人：
     * <li>修改时间：
     */
    public static List<String> lrange(String key, int start, int stop) {
        try {
            return jedisCluster.lrange(key, start, stop);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <li>@Description:批量获取map
     * <li>@param keys
     * <li>@return
     * <li>创建人：韩啸
     * <li>创建时间：2016年9月23日
     * <li>修改人：
     * <li>修改时间：
     */
    public static Map<String, Long> llenbatch(final List<String> keys) {

        try {
            Map<String, Long> value = new HashMap<String, Long>();
            for (String key : keys) {
                value.put(key, jedisCluster.llen(key));
            }
            return value;
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <li>@Description:批量获取map
     * <li>@param keys
     * <li>@return
     * <li>创建人：韩啸
     * <li>创建时间：2016年8月18日
     * <li>修改人：
     * <li>修改时间：
     */
    public static List<Map<String, String>> hgetallBatch(final List<String> keys) {

        try {
            List<Map<String, String>> value = new ArrayList<Map<String, String>>();
            for (String key : keys) {
                value.add(jedisCluster.hgetAll(key));
            }
            return value;
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static void hdel(String key, String field) {
        try {
            jedisCluster.hdel(key, field);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    /**
     * <br> 原子操作 一次性取出并删除
     *
     * @param key
     * @param filed
     * @return
     * @author 韩啸
     * @date 2016年11月10日  上午4:08:51
     */
    public static String hgetanddel(String key, String filed) {
        String res = null;
        try {
            Object o = jedisCluster.eval(" return redis.call('hget',KEYS[1],ARGV[1]),redis.call('hdel',KEYS[1],ARGV[1])", 1, key, filed);
            if (o == null) {
                res = null;
            } else {
                res = o + "";
            }
        } catch (Exception e) {
            log.info("", e);
        }
        return res;

    }

    /**
     * @param script   脚本代码
     * @param keycount 参数列表里前N个是key
     * @param par      参数数组
     * @return
     * @author hx
     * @Date: 2017年6月7日 下午8:45:41
     * @desc 执行lua脚本
     */
    public static String eval(String script, int keycount, String... par) {
        String res = null;
        try {
            Object o = jedisCluster.eval(script, keycount, par);
            if (o == null) {
                res = null;
            } else {
                res = o + "";
            }
        } catch (Exception e) {
            log.info("", e);
        }
        return res;

    }


    /**
     * @param key
     * @param count
     * @param field
     * @return
     */
    public static Long lrem(String key, int count, String field) {
        try {
            return jedisCluster.lrem(key, count, field);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * Redis LLEN命令将返回存储在key列表的长度。
     * 如果key不存在，它被解释为一个空列表，则返回0。
     * 当存储在关key的值不是一个列表，则会返回错误
     *
     * @param key
     * @return
     */
    public static Long llen(String key) {
        try {
            return jedisCluster.llen(key);
        } catch (Exception e) {
            log.info("", e);
            return 0l;
        }
    }


    /**
     * 发布消息
     *
     * @param channel
     * @param message
     */
    public static void publish(final String channel, final String message) {
        try {
            jedisCluster.publish(channel, message);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    public static void subscribe(final JedisPubSub jedisPubSub, final String channel) {
        try {
            jedisCluster.subscribe(jedisPubSub, channel);
        } catch (Exception e) {
            log.info("", e);
        }
    }


    public static Long setnx(final String key, final String value) {
        try {
            return jedisCluster.setnx(key, value == null ? "" : value);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 设置string  EX模式 只在键不存在时，才对键进行设置操作。
     *
     * @param key
     * @param value
     * @param seconds 单位 秒
     * @return 成功返回true  失败返回false
     * @author 韩啸
     * @date 2016年11月7日  上午4:34:24
     */
    public static Boolean setnxex(final String key, final String value, int seconds) {
        try {
            return jedisCluster.set(key, value == null ? "" : value, "NX", "EX", seconds) != null;
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static List<String> brpop(final int timeout, final String key) {
        try {
            return jedisCluster.brpop(timeout, key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }


    public static Set<String> hkeys(final String key) {
        try {
            return jedisCluster.hkeys(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 往sortedset添加一条记录
     *
     * @param key    key
     * @param score  排序值
     * @param member value
     * @return
     * @author 韩啸
     * @date 2016年11月2日  下午3:06:10
     */
    public static Long zadd(String key, Double score, String member) {
        try {
            return jedisCluster.zadd(key, score, member);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 批量插入sortedset
     *
     * @param key          key
     * @param scoreMembers map的key是value  value是score
     * @return
     * @author 韩啸
     * @date 2016年11月2日  下午3:08:55
     */
    public static Long zadd(String key, Map<String, Double> scoreMembers) {
        try {
            return jedisCluster.zadd(key, scoreMembers);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 根据score删除sortedset里的数据
     *
     * @param key
     * @param score 分值
     * @return
     * @author 韩啸
     * @date 2016年11月2日  下午3:12:04
     */
    public static Long zremByScore(String key, double score) {
        try {
            return jedisCluster.zremrangeByScore(key, score, score);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static Long zrem(String key, String member) {
        try {
            return jedisCluster.zrem(key, member);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 返回member在sortedset里面的score  不存在为-1
     *
     * @param key
     * @param member
     * @return
     * @author 韩啸
     * @date 2016年11月7日  下午9:41:36
     */
    public static Double zscore(String key, String member) {
        try {
            Double d = jedisCluster.zscore(key, member);
            d = d == null ? -1d : d;
            return d;
        } catch (Exception e) {
            log.info("", e);
            return -1d;
        }
    }

    /**
     * <br> 详细说明 TODO
     *
     * @param key
     * @param max
     * @param min
     * @return
     * @author 韩啸
     * @date 2016年11月2日  下午3:47:21
     */
    public static Set<String> zrevrange(String key, long max, long min) {
        try {
            return jedisCluster.zrevrange(key, max, min);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 详细说明 TODO
     *
     * @param key
     * @param max
     * @param min
     * @return
     * @author 韩啸
     * @date 2016年11月7日  下午10:13:15
     */
    public static Set<String> zrangeByScore(String key, long min, long max) {
        try {

            return jedisCluster.zrangeByScore(key, min, max);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 详细说明 set的add方法
     *
     * @param key
     * @param member
     * @return
     * @author 韩啸
     * @date 2016年12月5日  上午12:21:17
     */
    public static Long sadd(String key, String... member) {
        try {
            return jedisCluster.sadd(key, member);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * <br> 详细说明 根据key取出set集合
     *
     * @param key
     * @return
     * @author 韩啸
     * @date 2016年12月5日  上午12:22:11
     */
    public static Set<String> smembers(String key) {
        try {
            return jedisCluster.smembers(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * get 取key值对应的数据
     *
     * @param key
     * @return
     */
    public static byte[] get(byte[] key) {
        try {
            return jedisCluster.get(key);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    /**
     * @param key
     * @param obj
     * @return
     */
    public static String set(byte[] key, byte[] obj) {
        try {
            return jedisCluster.set(key, obj);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static List<String> hmget(String key, final String... fields) {
        try {
            return jedisCluster.hmget(key, fields);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static Long zrank(String key, String number) {
        try {
            return jedisCluster.zrank(key, number);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static Set<String> zrange(String key, long start, long end) {
        try {
            return jedisCluster.zrange(key, start, end);
        } catch (Exception e) {
            log.info("", e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("执行开始...");
        for (int i =1 ; i<= 100; i++){
            JedisClusterUtil.set("name"+i,"cuihui"+i);
        }
        System.out.println("执行结束...");
        System.exit(0);
    }
}