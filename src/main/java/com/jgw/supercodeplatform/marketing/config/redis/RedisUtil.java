package com.jgw.supercodeplatform.marketing.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author liujianqiang
 * @date 2018年9月5日
 */
@Component("redisUtil")
//@Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
public class RedisUtil {
	
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 写入key value
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入key value设置过期时间,单位秒
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key, String value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     * @author liujianqiang
     * @data 2018年9月5日
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @author liujianqiang
     * @data 2018年9月5日
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern);
        if (keys.size() > 0) {
            stringRedisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            stringRedisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @return
     */
    public String get(final String key) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 添加hash数据
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 读取hash数据
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 判断key和hashkey是否存在
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @param hashKey
     * @return
     */
    public boolean isHmKey(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        return hash.hasKey(key, hashKey);
    }

   /**
    * 删除key和hashkey
    * @author liujianqiang
    * @data 2018年9月5日
    * @param key
    * @param hashKey
    * @return
    */
    public Long deleteHmKey(String key, Object... hashKey) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        return hash.delete(key, hashKey);
    }

    /**
     *  获取所有的hashkey的value值
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @return
     */
    public List<Object> getHashValues(String key) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        return hash.values(key);
    }


   /**
    * 获取对应Hashkey值的所有值
    * @author liujianqiang
    * @data 2018年9月5日
    * @param key
    * @return
    */
    public Set<Object> getHashKeys(String key) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        return hash.keys(key);
    }

    
    /**
     * 自增1并获取
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @return
     */
    public long generate(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }

    /**
     * 自增1，并获取，并且设置过期时间
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @param expireTime
     * @return
     */
    public long generate(String key, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.incrementAndGet();
    }
    
   /**
    * 自增increment并获取
    * @author liujianqiang
    * @data 2018年9月5日
    * @param key
    * @param increment
    * @return
    */
    public long generate(String key, long increment) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        return counter.addAndGet(increment);
    }

    /**
     * 自增increment并获取，并且设置过期时间
     * @author liujianqiang
     * @data 2018年9月5日
     * @param key
     * @param increment
     * @param expireTime
     * @return
     */
    public long generate(String key, long increment, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.addAndGet(increment);
    }

    /**
     * @author liujianqiang
     * @data 2018年7月24日
     * @param key
     * @param increment
     * @param timeOut
     * @return
     */
    public long generate(String key, long increment, long timeOut) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expire(timeOut, TimeUnit.SECONDS);
        return counter.addAndGet(increment);
    }
    /**
     * 设置key过期时间
     * @param key
     * @param timeout
     * @param unit，默认为妙
     * @return
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
    	if (null==unit) {
    		unit=TimeUnit.SECONDS;
		}
    	return stringRedisTemplate.expire(key, timeout, unit);
    }
    
    /**
     * 获取剩余过期时间
     * @param key
     * @return
     */
    public Long leftExpireSeconds(String key) {
    	return  stringRedisTemplate.getExpire(key);
    }
   
}