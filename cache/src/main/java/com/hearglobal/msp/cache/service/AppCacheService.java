package com.hearglobal.msp.cache.service;

import com.hearglobal.msp.cache.config.RedisConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 封装缓存操作 服务
 * Created by lvzhouyang on 16/12/22.
 */
@Service
public class AppCacheService implements IHedis {

    // 缓存超时时间，单位：秒 ,默认1天
    private static final int DEFAULT_EXPIRE_TIME = 60 * 60 * 24;
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisTemplate<Serializable, Object> redisObjectTemplate;

    /**
     * 设置缓存数据，指定超时时间
     *
     * @param key
     * @param value
     */
    @Override
    public void set(String key, int expireTime, Object value) {
        redisObjectTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存数据，使用默认超时时间
     *
     * @param key
     * @param value
     */
    @Override
    public void set(String key, Object value) {
        this.set(key, DEFAULT_EXPIRE_TIME, value);
    }

    /**
     * 根据键值获取缓存值
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        Object value = null;

        try {
            value = redisObjectTemplate.opsForValue().get(key);
            logger.debug("Get object from key [" + key + "] by redisObjectTemplate ->" + ToStringBuilder.reflectionToString(value));
            return value;
        } catch (Exception th) {
            //缓存cache不应该影响应用提供服务
            logger.warn("Failed to get object from key [" + key + "] by redisObjectTemplate");
            logger.error(th.getMessage(), th);
            return null;
        }
    }

    /**
     * 删除缓存数据
     *
     * @param key
     */
    @Override
    public void delete(String key) {
        redisObjectTemplate.delete(key);
    }

    /**
     * 入队
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public void listPush(String key, Object value) {
        redisObjectTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 出队
     *
     * @param key
     * @return
     */
    @Override
    public Object listPop(String key) {
        return redisObjectTemplate.opsForList().leftPop(key);
    }

    /**
     * 栈/队列长
     *
     * @param key
     * @return
     */
    @Override
    public Long listLen(String key) {
        return redisObjectTemplate.opsForList().size(key);
    }


    /**
     * 增长指定大小
     *
     * @param key
     * @param length
     * @return
     */
    @Override
    public Long incr(String key, Long length) {
        return redisObjectTemplate.opsForValue().increment(key, length);
    }

    /**
     * 每次加一
     *
     * @param key
     * @return
     */
    @Override
    public Long incr(String key) {
        return incr(key, 1L);
    }

    /**
     * 元素分数增加，delta是增量
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    @Override
    public Double incrementScore(String key, Object value, double delta) {
        return redisObjectTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），正序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key, long start, long end) {
        return redisObjectTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），倒序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        return redisObjectTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 键为K的集合，value为obj的元素索引，正序
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public Long rank(String key, Object value) {
        return redisObjectTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 键为K的集合，value为obj的元素索引，倒序
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public Long reverseRank(String key, Object value) {
        return redisObjectTemplate.opsForZSet().reverseRank(key, value);
    }

    @Override
    public Double score(String key, Object value) {
        return redisObjectTemplate.opsForZSet().score(key, value);
    }

    /**
     * 键为K的集合元素个数
     *
     * @param key
     */
    @Override
    public Long zsetSize(String key) {
        return redisObjectTemplate.opsForZSet().size(key);
    }

    @Override
    public Long remove(String key, Object... values) {
        return redisObjectTemplate.opsForZSet().remove(key, values);
    }

    @Override
    public Long removeRange(String key, long start, long end) {
        return redisObjectTemplate.opsForZSet().remove(key, start, end);
    }

    @Override
    public Long removeRangeByScore(String key, double min, double max) {
        return redisObjectTemplate.opsForZSet().remove(key, min, max);
    }


}
