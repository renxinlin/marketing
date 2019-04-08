package com.jgw.supercodeplatform.marketing.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 描述：分布式锁工具类
 * <p>
 * Created by corbett on 2019/3/30.
 */
public class RedisLockUtil extends AbstractRedisLock {

    private final Logger logger = LoggerFactory.getLogger(RedisLockUtil.class);

    private StringRedisTemplate redisTemplate;

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    public RedisLockUtil(StringRedisTemplate redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = setLock(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                logger.debug("互殴锁失败，重试中:" + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = setLock(key, expire);
        }
        return result;
    }

    private boolean setLock(String key, long expire) {
        try {
            String result = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    JedisCommands jedisCommands = (JedisCommands) redisConnection.getNativeConnection();
                    String uuid = UUID.randomUUID().toString();
                    lockFlag.set(uuid);
                    return jedisCommands.set(key, uuid, "NX", "PX", expire);
                }
            });
            logger.debug("redis setnx执行结果：" + result);
            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("获取分布式锁出错：" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> args = new ArrayList<String>();
            args.add(lockFlag.get());

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本

            Long result = redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    Object nativeConnection = connection.getNativeConnection();
                    // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                    // 集群模式
                    if (nativeConnection instanceof JedisCluster) {
                        return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                    }

                    // 单机模式
                    else if (nativeConnection instanceof Jedis) {
                        return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                    }
                    return 0L;
                }
            });

            return result != null && result > 0;
        } catch (Exception e) {
            logger.error("释放锁出错", e);
        }
        return false;
    }
}