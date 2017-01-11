package com.hearglobal.msp.cache.service;

/**
 * Created by lvzhouyang on 17/1/11.
 */
public interface IHedis {

    void set(String key, int expireTime, Object value);

    void set(String key, Object value);

    Object get(String key);

    void delete(String key);

    void listPush(String key, Object value);

    Object listPop(String key);

    Long listLen(String key);

    Long incr(String key, Long length);

    Long incr(String key);
}
