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
import org.springframework.web.client.RestTemplate;

import java.util.Map;
/**
 *                                         ,s555SB@@&
 *                                      :9H####@@@@@Xi
 *                                     1@@@@@@@@@@@@@@8
 *                                   ,8@@@@@@@@@B@@@@@@8
 *                                  :B@@@@X3hi8Bs;B@@@@@Ah,
 *             ,8i                  r@@@B:     1S ,M@@@@@@#8;
 *            1AB35.i:               X@@8 .   SGhr ,A@@@@@@@@S
 *            1@h31MX8                18Hhh3i .i3r ,A@@@@@@@@@5
 *            ;@&i,58r5                 rGSS:     :B@@@@@@@@@@A
 *             1#i  . 9i                 hX.  .: .5@@@@@@@@@@@1
 *              sG1,  ,G53s.              9#Xi;hS5 3B@@@@@@@B1
 *               .h8h.,A@@@MXSs,           #@H1:    3ssSSX@1
 *               s ,@@@@@@@@@@@@Xhi,       r#@@X1s9M8    .GA981
 *               ,. rS8H#@@@@@@@@@@#HG51;.  .h31i;9@r    .8@@@@BS;i;
 *                .19AXXXAB@@@@@@@@@@@@@@#MHXG893hrX#XGGXM@@@@@@@@@@MS
 *                s@@MM@@@hsX#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&,
 *              :GB@#3G@@Brs ,1GM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@B,
 *            .hM@@@#@@#MX 51  r;iSGAM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@8
 *          :3B@@@@@@@@@@@&9@h :Gs   .;sSXH@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:
 *      s&HA#@@@@@@@@@@@@@@M89A;.8S.       ,r3@@@@@@@@@@@@@@@@@@@@@@@@@@@r
 *   ,13B@@@@@@@@@@@@@@@@@@@5 5B3 ;.         ;@@@@@@@@@@@@@@@@@@@@@@@@@@@i
 *  5#@@#&@@@@@@@@@@@@@@@@@@9  .39:          ;@@@@@@@@@@@@@@@@@@@@@@@@@@@;
 *  9@@@X:MM@@@@@@@@@@@@@@@#;    ;31.         H@@@@@@@@@@@@@@@@@@@@@@@@@@:
 *   SH#@B9.rM@@@@@@@@@@@@@B       :.         3@@@@@@@@@@@@@@@@@@@@@@@@@@5
 *     ,:.   9@@@@@@@@@@@#HB5                 .M@@@@@@@@@@@@@@@@@@@@@@@@@B
 *           ,ssirhSM@&1;i19911i,.             s@@@@@@@@@@@@@@@@@@@@@@@@@@S
 *              ,,,rHAri1h1rh&@#353Sh:          8@@@@@@@@@@@@@@@@@@@@@@@@@#:
 *            .A3hH@#5S553&@@#h   i:i9S          #@@@@@@@@@@@@@@@@@@@@@@@@@A.
 *
 *
 * 又看源码，看你妹妹呀！
 *
 * @author lvzhouyang
 * @Description 封装api基类
 * @create 2017-02-10-上午9:04
 */
public class HttpRemoteService {

    protected Logger logger = LoggerFactory.getLogger(HttpRemoteService.class);

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
            Map map = MapUtils.getMap(response, ERROR);
            Error error =  new Error(MapUtils.getString(map,"code"), MapUtils.getString(map,"requestUri"), MapUtils.getString(map,"message"));
            throw new RemoteCallException(error, NumberUtils.toInt(MapUtils.getString(map,"code")));
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