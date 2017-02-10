package com.hearglobal.msp.core.remote;

import com.google.common.collect.Maps;
import com.hearglobal.msp.api.Error;
import com.hearglobal.msp.util.JsonUtils;

import java.util.Map;

/**
 * The type Api response.
 * api接口 返回值封装
 *
 * @author lvzhouyang
 * @version 1.0
 * @create 2017 -02-10-上午10:07
 * @since 2017.02.10
 */
public class ApiResponse {

    /**
     * 带返回值 请求成功
     *
     * @param data the data
     * @return map
     * @since 2017.02.10
     */
    public static Map<String, Object> createApiSuccessMap(Object data) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 1);
        ret.put("data", JsonUtils.object2Json(data));
        return ret;
    }

    /**
     * 失败请求
     *
     * @param error
     * @return
     */
    public static Map<String, Object> createApiError(Error error) {
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("status", 0);
        ret.put("error", error);
        return ret;
    }
}
