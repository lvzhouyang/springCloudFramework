package com.hearglobal.msp.api;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by lvzhouyang on 16/12/14.
 */
public class AjaxResult {
    public static Map<String, Object> createAjaxSuccessMap(String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> createSuccessMap() {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("msg", "isSuccess");
        return ret;
    }

    public static Map<String, Object> createAjaxSuccessMap(Object data) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("data", data);
        return ret;
    }

    public static Map<String, Object> createAjaxSuccessMap(Object data, String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("data", data);
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> createAjaxFailMap(String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> createAjaxFailMap(Object data) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("data", data);
        return ret;
    }

    public static Map<String, Object> createAjaxFailMap(Object data, String msg) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("msg", msg);
        ret.put("data", data);
        return ret;
    }
}
