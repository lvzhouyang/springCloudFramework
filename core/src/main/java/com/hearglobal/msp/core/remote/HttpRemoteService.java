package com.hearglobal.msp.core.remote;

import com.alibaba.fastjson.JSON;
import com.hearglobal.msp.api.CommonErrorCode;
import com.hearglobal.msp.api.Error;
import com.hearglobal.msp.core.exception.AppBusinessException;
import com.hearglobal.msp.core.exception.RemoteCallException;
import com.hearglobal.msp.util.ObjectUtil;
import com.hearglobal.msp.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author lvzhouyang
 * @Description 封装api基类
 * @create 2017-02-10-上午9:04
 */
public class HttpRemoteService {

    protected Logger logger = LoggerFactory.getLogger(HttpRemoteService.class);

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    public static String DATA = "data";
    public static String STATUS = "status";
    public static String ERROR = "error";

    public <T> T invoke(Map<String, Object> response, TypeReference<T> typeReference) {
        if (MapUtils.isEmpty(response)) {
            throw new AppBusinessException("请求失败!");
        }
        Integer status = MapUtils.getInteger(response, STATUS);
        // 请求失败的处理
        if (ObjectUtil.isNullObj(status)
                || status.equals(NumberUtils.INTEGER_ZERO)) {
            Error error = (Error) MapUtils.getObject(response, ERROR);
            throw new RemoteCallException(error, NumberUtils.toInt(error.getCode()));
        }

        String data = MapUtils.getString(response, DATA);
        if (StringUtil.isEmpty(data)) {
            throw new RemoteCallException(getDefaultError(), NumberUtils.toInt(getDefaultError().getCode()));
        }
        try {
            return (T) JSON.parseObject(data, typeReference);
        } catch (Exception e) {
            throw new RemoteCallException(getDefaultError(), NumberUtils.toInt(getDefaultError().getCode()));
        }
    }

    /**
     * get template
     *
     * @param url
     * @param uriVariables
     * @param typeReference
     * @param <T>
     * @return
     */
    public <T> T invokeGet(String url, Map<String, Object> uriVariables, TypeReference<T> typeReference) {
        // check
        this.invokeGetCheck(url);
        Map<String, Object> result;
        try {
            result = this.restTemplate.getForObject(url, Map.class, uriVariables);
        } catch (Exception e) {
            logger.error("HttpRemoteService invokeGet error url:{},uriVariables:{},typeReference:{}", url, ObjectUtil.toString(uriVariables), ObjectUtil.toString(typeReference));
            throw new RemoteCallException(getDefaultError(), NumberUtils.toInt(getDefaultError().getCode()));
        }
        return this.invoke(result, typeReference);
    }

    private void invokeGetCheck(String url) {
        if (StringUtil.isEmpty(url)) {
            throw new RemoteCallException(new Error(CommonErrorCode.BAD_REQUEST.getCode(), "", "请求的URL为空!")
                    , NumberUtils.toInt(CommonErrorCode.BAD_REQUEST.getCode()));
        }
    }


    private Error getDefaultError() {
        return new Error(CommonErrorCode.INTERNAL_ERROR.getCode(), "", "发生未知错误!");
    }
}