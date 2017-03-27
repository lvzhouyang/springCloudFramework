package com.hearglobal.msp.cache.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * 缓存常用操作接口
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public interface IHedis {

    /**
     * 设置缓存数据，指定超时时间
     *
     * @param key        the key
     * @param expireTime the expire time
     * @param value      the value
     * @since 2017.03.27
     */
    void set(String key, int expireTime, Object value);

    /**
     * 设置缓存数据，使用默认超时时间
     *
     * @param key   the key
     * @param value the value
     * @since 2017.03.27
     */
    void set(String key, Object value);

    /**
     * 根据键值获取缓存值
     *
     * @param key the key
     * @return object
     * @since 2017.03.27
     */
    Object get(String key);

    /**
     * 删除缓存数据
     *
     * @param key the key
     * @since 2017.03.27
     */
    void delete(String key);

    /**
     * 入队
     *
     * @param key   the key
     * @param value the value
     * @return
     * @since 2017.03.27
     */
    void listPush(String key, Object value);

    /**
     * 出队
     *
     * @param key the key
     * @return object
     * @since 2017.03.27
     */
    Object listPop(String key);

    /**
     * 栈/队列长
     *
     * @param key the key
     * @return long
     * @since 2017.03.27
     */
    Long listLen(String key);

    /**
     * 增长指定大小
     *
     * @param key    the key
     * @param length the length
     * @return long
     * @since 2017.03.27
     */
    Long incr(String key, Long length);

    /**
     * 每次加一
     *
     * @param key the key
     * @return long
     * @since 2017.03.27
     */
    Long incr(String key);

    /**
     * 元素分数增加，delta是增量
     *
     * @param key   the key
     * @param value the value
     * @param delta the delta
     * @return the double
     * @since 2017.03.27
     */
    Double incrementScore(String key, Object value, double delta);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），正序
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the set
     * @since 2017.03.27
     */
    Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key, long start, long end);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），倒序
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the set
     * @since 2017.03.27
     */
    Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end);

    /**
     * 键为K的集合，value为obj的元素索引，正序
     *
     * @param key   the key
     * @param value the value
     * @return the long
     * @since 2017.03.27
     */
    Long rank(String key, Object value);

    /**
     * 键为K的集合，value为obj的元素索引，倒序
     *
     * @param key   the key
     * @param value the value
     * @return the long
     * @since 2017.03.27
     */
    Long reverseRank(String key, Object value);

    /**
     * 获取键为key的集合，value为obj的元素分数
     *
     * @param key the key
     * @param obj the obj
     * @return double
     * @since 2017.03.27
     */
    Double score(String key,Object obj);

    /**
     * 键为K的集合元素个数
     *
     * @param key the key
     * @return the long
     * @since 2017.03.27
     */
    Long zsetSize(String key);

    /**
     * Remove long.
     *
     * @param key    the key
     * @param values the values
     * @return the long
     * @since 2017.03.27
     */
    Long remove(String key, Object... values);

    /**
     * Remove range long.
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the long
     * @since 2017.03.27
     */
    Long removeRange(String key, long start, long end);

    /**
     * Remove range by score long.
     *
     * @param key the key
     * @param min the min
     * @param max the max
     * @return the long
     * @since 2017.03.27
     */
    Long removeRangeByScore(String key, double min, double max);

    /**
     * 查看ihedis 中set 集合的size
     *
     * @param key the key
     * @return Long size
     */
    Long setSize(String key);

    /**
     * 往ihedis 中set 添加元素
     *
     * @param key   the key
     * @param value the value
     * @return add
     */
    Long setAdd(String key,Object... value);

    /**
     * 根据key 取 ihedis set
     *
     * @param key the key
     * @return set
     */
    Set<Object> getSet(String key);
}
