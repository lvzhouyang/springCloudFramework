package com.hearglobal.msp.cache.service;

import com.hearglobal.msp.cache.config.RedisConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 封装缓存操作 服务
 * Created by lvzhouyang on 16/12/22.
 */
@Service
public class AppCacheService {

    // 缓存超时时间，单位：秒 ,默认1天
    private static final int DEFAULT_EXPIRE_TIME = 60 * 60 * 24;
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisTemplate<String, Object> redisObjectTemplate;

    /**
     * 设置缓存数据，指定超时时间
     *
     * @param key
     * @param value
     */
    public void set(String key, int expireTime, Object value) {
        redisObjectTemplate.opsForValue().set(key, value, expireTime);
    }

    /**
     * 设置缓存数据，使用默认超时时间
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisObjectTemplate.opsForValue().set(key, value, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 根据键值获取缓存值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        Object value = null;

        try {
            redisObjectTemplate.opsForValue().get(key);
        } catch (Exception th) {
            //缓存cache不应该影响应用提供服务
            logger.error(th.getMessage(), th);
        }

        if (value == null) {
            logger.warn("Failed to get object from key [" + key + "] by redisObjectTemplate");
            return null;
        } else {
            logger.debug("Get object from key [" + key + "] by redisObjectTemplate ->" + ToStringBuilder.reflectionToString(value));
            return value;
        }
    }

    /**
     * 删除缓存数据
     *
     * @param key
     */
    public void delete(String key) {
        redisObjectTemplate.delete(key);
    }

    /**
     * 批量取缓存
     *
     * @param keys
     * @return
     */
    public List<Object> multiGet(List<String> keys) {
        return redisObjectTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 入队
     *
     * @param key
     * @param value
     * @return
     */
    public void listPush(String key, String value) {
        redisObjectTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 出队
     *
     * @param key
     * @return
     */
    public Object listPop(String key) {
        return redisObjectTemplate.opsForList().leftPop(key);
    }

    /**
     * 栈/队列长
     *
     * @param key
     * @return
     */
    public Long listLen(String key) {
        return redisObjectTemplate.opsForList().size(key);
    }


    /**
     * 增长指定大小
     * @param key
     * @param length
     * @return
     */
    public Long incr(String key, Long length){
        return redisObjectTemplate.opsForValue().increment(key,length);
    }

    /**
     * 每次加一
     * @param key
     * @return
     */
    public Long incr(String key){
        return incr(key,1L);
    }
}
