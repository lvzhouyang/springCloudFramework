package com.hearglobal.msp.cache.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * Created by lvzhouyang on 17/1/11.
 * 缓存接口
 */
public interface IHedis {

    /**
     * 设置缓存数据，指定超时时间
     * @param key
     * @param expireTime
     * @param value
     */
    void set(String key, int expireTime, Object value);

    /**
     * 设置缓存数据，使用默认超时时间
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 根据键值获取缓存值
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除缓存数据
     *
     * @param key
     */
    void delete(String key);

    /**
     * 入队
     *
     * @param key
     * @param value
     * @return
     */
    void listPush(String key, Object value);

    /**
     * 出队
     *
     * @param key
     * @return
     */
    Object listPop(String key);

    /**
     * 栈/队列长
     *
     * @param key
     * @return
     */
    Long listLen(String key);

    /**
     * 增长指定大小
     * @param key
     * @param length
     * @return
     */
    Long incr(String key, Long length);

    /**
     * 每次加一
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 元素分数增加，delta是增量
     **/
    Double incrementScore(String key, Object value, double delta);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），正序
     **/
    Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key, long start, long end);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集，返回泛型接口（包括score和value），倒序
     **/
    Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end);

    /**
     * 键为K的集合，value为obj的元素索引，正序
     **/
    Long rank(String key, Object value);

    /**
     * 键为K的集合，value为obj的元素索引，倒序
     **/
    Long reverseRank(String key, Object value);

    /**
     * 获取键为key的集合，value为obj的元素分数
     * @param key
     * @param obj
     * @return
     */
    Double score(String key,Object obj);

    /**
     * 键为K的集合元素个数
     **/
    Long zsetSize(String key);

    Long remove(String key, Object... values);

    Long removeRange(String key, long start, long end);

    Long removeRangeByScore(String key, double min, double max);

    /**
     * 查看ihedis 中set 集合的size
     * @param key
     * @return Long
     */
    Long iHedisSetSize(String key);

    /**
     * 往ihedis 中set 添加元素
     * @param key
     * @param value
     * @return
     */
    Long addInIHedisSet(String key,Object... value);

    /**
     * 根据key 取 ihedis set
     * @param key
     * @return
     */
    Set<Object> getIHedisSetByKey(String key);
}
