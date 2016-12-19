package com.hearglobal.msp.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hearglobal.msp.core.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * Created by lvzhouyang on 16/12/14.
 */
@Configuration
@EnableCaching
@EnableRedisHttpSession
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    protected Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Autowired
    private RedisProperties redisProperties;


    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisProperties properties = redisProperties;
        this.checkProperties(properties);

        JedisConnectionFactory factory = new JedisConnectionFactory();
        logger.info("jedis factory init host:{},port:{}", properties.getHost(), properties.getPort());
        factory.setHostName(properties.getHost());
        factory.setPort(NumberUtils.toInt(properties.getPort()));
        factory.setPassword(properties.getPassword());
        factory.setTimeout(NumberUtils.toInt(properties.getTimeout())); //设置连接超时时间
        factory.setDatabase(redisProperties.getDatabase());
        return factory;
    }

    private void checkProperties(RedisProperties properties) {
        if (StringUtils.isEmpty(properties.getHost())) {
            logger.error("初始化redis连接失败,请配置redis host!");
            throw new BaseException("初始化redis连接失败,请配置redis host!");
        }

        if (StringUtils.isEmpty(properties.getPort())) {
            logger.error("初始化redis连接失败,请配置redis port!");
            throw new BaseException("初始化redis连接失败,请配置redis port!");
        }
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(10); //设置key-value超时时间
        return cacheManager;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }

    @Bean
    public Jedis jedis() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        RedisProperties properties = redisProperties;
        this.checkProperties(properties);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost()
                , NumberUtils.toInt(redisProperties.getPort()), NumberUtils.toInt(redisProperties.getTimeout())
                , redisProperties.getPassword());
        return jedisPool.getResource();
    }
}