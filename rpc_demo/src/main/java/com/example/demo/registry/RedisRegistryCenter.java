package com.example.demo.registry;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:44
 */
//注册中心，现在用的redis实现，考虑使用zoomkeeper试试能达到更好的效果
public class RedisRegistryCenter {

    private static Jedis jedis;
    public static void init(String host, int port){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);
        JedisPool jedisPool = new JedisPool(config, host, port);
        jedis = jedisPool.getResource();
    }

    /**
     * 注册生产者
     *
     * @param nozzle 接口
     * @param alias  别名
     * @param info   信息
     * @return 注册结果
     */
    public static Long registryProvider(String nozzle, String alias, String info) {
        return jedis.sadd(nozzle + "_" + alias, info);
    }
    /**
     * 获取生产者
     * 模拟权重，随机获取
     * @param nozzle 接口名称
     */
    public static String obtainProvider(String nozzle, String alias) {
        return jedis.srandmember(nozzle + "_" + alias);
    }
    public static Jedis jedis() {
        return jedis;
    }

}
