package com.hearglobal.msp.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ThreadLocal
 * @author lvzhouyang
 */
public class AppThreadCache {
    private static final ThreadLocal<ThreadContext> cache = new ThreadLocal<ThreadContext>() {
        protected ThreadContext initialValue() {
            return new ThreadContext();
        }
    };

    private static class ThreadContext {
        Integer staffId;
        long startTime;
        Map<String, Object> extraData = new ConcurrentHashMap<String, Object>();
    }

    public static void setExtraData(String key, Object data) {
        cache.get().extraData.put(key, data);
    }

    public static Object getExtraData(String key) {
        return cache.get().extraData.get(key);
    }

    public static void setStartTime() {
        cache.get().startTime = System.currentTimeMillis();
    }

    public static long getStartTime() {
        return cache.get().startTime;
    }

    public static void setStaffId(Integer staffId) {
        cache.get().staffId = staffId;
    }

    public static long getStaffId() {
        return cache.get().staffId;
    }
}
