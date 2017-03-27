package com.hearglobal.msp.api;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 给前端的返回值封装
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public class AjaxResult {
    /**
     * Instantiates a new Ajax result.
     */
    private AjaxResult() {
    }

    /**
     * 返回成功消息
     *
     * @param msg the msg
     * @return map
     * @since 2017.03.27
     */
    public static Map<String, Object> createAjaxSuccessMap(String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("msg", msg);
        return ret;
    }

    /**
     * 返回成功默认消息
     *
     * @return map
     * @since 2017.03.27
     */
    public static Map<String, Object> createSuccessMap() {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("msg", "isSuccess");
        return ret;
    }

    /**
     * 返回成功数据
     *
     * @param data the data
     * @return map
     * @since 2017.03.27
     */
    public static Map<String, Object> createAjaxSuccessMap(Object data) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("data", data);
        return ret;
    }


    /**
     * Create ajax success map map.
     *
     * @param data the data
     * @param page the page
     * @return the map
     * @since 2017.03.27
     */
    public static Map<String, Object> createAjaxSuccessMap(Object data, Page page) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("data", data);
        ret.put("page", page);
        return ret;
    }

    /**
     * 返回成功消息和数据
     *
     * @param data the data
     * @param msg  the msg
     * @return map
     * @since 2017.03.27
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
     *
     * @param msg the msg
     * @return map
     * @since 2017.03.27
     */
    public static Map<String, Object> createAjaxFailMap(String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("msg", msg);
        return ret;
    }

    /**
     * 返回失败数据
     *
     * @param data the data
     * @return map
     * @since 2017.03.27
     */
    public static Map<String, Object> createAjaxFailMap(Object data) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("data", data);
        return ret;
    }

    /**
     * 返回失败消息和数据
     *
     * @param data the data
     * @param msg  the msg
     * @return map
     * @since 2017.03.27
     */
    public static Map<String, Object> createAjaxFailMap(Object data, String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("msg", msg);
        ret.put("data", data);
        return ret;
    }
}
