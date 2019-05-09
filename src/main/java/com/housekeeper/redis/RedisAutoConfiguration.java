package com.housekeeper.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author yezy
 * @since 2018/8/16
 */
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties properties;

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public JedisConnectionFactory redisConnectionFactory() {
        return applyProperties(createJedisConnectionFactory());
    }

    private JedisConnectionFactory applyProperties(JedisConnectionFactory factory) {
        configureConnection(factory);
//        if (this.properties.isSsl()) {
//            factory.setUseSsl(true);
//        }
//        factory.setDatabase(this.properties.getDatabase());
//        if (this.properties.getTimeout() > 0) {
//            factory.setTimeout(this.properties.getTimeout());
//        }
        return factory;
    }

    private void configureConnection(JedisConnectionFactory factory) {
        factory.setHostName(this.properties.getHost());
        factory.setPort(this.properties.getPort());
        if (this.properties.getPassword() != null) {
            factory.setPassword(renderPassword());
        }
    }

    private String renderPassword() {
        String password = this.properties.getPassword();
        return password;
    }

    private JedisConnectionFactory createJedisConnectionFactory() {
//        JedisPoolConfig poolConfig = this.properties.getPool() != null ? jedisPoolConfig() : new JedisPoolConfig();
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        return new JedisMonitorConnectionFactory(poolConfig);
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
//        RedisProperties.Pool props = this.properties.getPool();
//        config.setMaxTotal(props.getMaxActive());
//        config.setMaxIdle(props.getMaxIdle());
//        config.setMinIdle(props.getMinIdle());
//        config.setMaxWaitMillis(props.getMaxWait());
        return config;
    }
}
