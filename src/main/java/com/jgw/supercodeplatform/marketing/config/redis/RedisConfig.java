package com.jgw.supercodeplatform.marketing.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration("redisConfigNew")
public class RedisConfig {

    // Redis服务器地址
    @Value("${redis.host}")
    private String host;
    
    // Redis服务器连接端口
    @Value("${redis.port}")
    private int port;
    // Redis服务器连接密码（默认为空）
    @Value("${redis.password}")
    private String password;
    // 连接超时时间（毫秒）
    @Value("${redis.timeout}")
    private int timeout;
    // 连接超时时间（毫秒）
    @Value("${redis.database}")
    private int database;
    // 连接池最大连接数（使用负值表示没有限制）
    @Value("${redis.pool.max-active}")
    private int maxTotal;
    // 连接池最大阻塞等待时间（使用负值表示没有限制）
    @Value("${redis.pool.max-wait}")
    private int maxWaitMillis;
    // 连接池中的最大空闲连接
    @Value("${redis.pool.max-idle}")
    private int maxIdle;
    // 连接池中的最小空闲连接
    @Value("${redis.pool.min-idle}")
    private int minIdle;

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 连接池最大连接数（使用负值表示没有限制）
        jedisPoolConfig.setMaxTotal(this.maxTotal);
        // 连接池最大阻塞等待时间（使用负值表示没有限制）
        jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
        // 连接池中的最大空闲连接
        jedisPoolConfig.setMaxIdle(this.maxIdle);
        // 连接池中的最小空闲连接
        jedisPoolConfig.setMinIdle(this.minIdle);
//        jedisPoolConfig.setTestOnBorrow(true);
//        jedisPoolConfig.setTestOnCreate(true);
//        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    @Bean(name = "jedisConnectionFactory")
    public RedisConnectionFactory jedisConnectionFactory(@Qualifier(value = "jedisPoolConfig") JedisPoolConfig poolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(this.host);
        jedisConnectionFactory.setPort(this.port);
        jedisConnectionFactory.setPassword(this.password);//密码
        jedisConnectionFactory.setDatabase(this.database);
        return jedisConnectionFactory;
    }

	@Bean
	public StringRedisTemplate stringRedisTemplate(@Qualifier(value = "jedisConnectionFactory") RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		return template;
	}

	@Bean
	public RedissonClient getRedisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" +host+":"+port).setPassword(password).setDatabase(database);
        // 默认编码为org.redisson.codec.JsonJacksonCodec
        // 数据兼容编码:org.springframework.data.redis.serializer.
		config.setCodec(new org.redisson.client.codec.StringCodec());
		config.useSingleServer().setConnectionPoolSize(maxTotal);
		config.useSingleServer().setIdleConnectionTimeout(100000);
		config.useSingleServer().setConnectTimeout(30000);
		config.useSingleServer().setTimeout(3000);
		config.useSingleServer().setPingTimeout(30000);
		config.useSingleServer().setReconnectionTimeout(3000);
        return Redisson.create(config);
    }


}

    
    
