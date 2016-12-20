package com.hearglobal.msp.api;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by lvzhouyang on 16/12/14.
 */
public class AjaxResult {
    private AjaxResult() {
    }

    /**
     * 返回成功消息
     * @param msg
     * @return
     */
    public static Map<String, Object> createAjaxSuccessMap(String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("msg", msg);
        return ret;
    }

    /**
     * 返回成功默认消息
     * @return
     */
    public static Map<String, Object> createSuccessMap() {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("msg", "isSuccess");
        return ret;
    }

    /**
     * 返回成功数据
     * @param data
     * @return
     */
    public static Map<String, Object> createAjaxSuccessMap(Object data) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("data", data);
        return ret;
    }

    /**
     * 返回成功消息和数据
     * @param data
     * @param msg
     * @return
     */
    public static Map<String, Object> createAjaxSuccessMap(Object data, String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("data", data);
        ret.put("msg", msg);
        return ret;
    }

    /**
     * 返回失败消息
     * @param msg
     * @return
     */
    public static Map<String, Object> createAjaxFailMap(String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("msg", msg);
        return ret;
    }

    /**
     * 返回失败数据
     * @param data
     * @return
     */
    public static Map<String, Object> createAjaxFailMap(Object data) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("data", data);
        return ret;
    }

    /**
     * 返回失败消息和数据
     * @param data
     * @param msg
     * @return
     */
    public static Map<String, Object> createAjaxFailMap(Object data, String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("msg", msg);
        ret.put("data", data);
        return ret;
    }
}
