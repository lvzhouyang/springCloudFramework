package com.hearglobal.msp.core.mvc.web.interceptor;

import com.hearglobal.msp.api.CommonErrorCode;
import com.hearglobal.msp.api.Error;
import com.hearglobal.msp.core.context.ApplicationConstant;
import com.hearglobal.msp.core.exception.RemoteCallException;
import com.hearglobal.msp.util.StringUtil;
import com.hearglobal.security.AuthUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统API调用鉴权
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.04.19
 */
@Component
public class ApiAuthInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ApplicationConstant applicationConstant;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = this.getUri(request);
        if (BooleanUtils.toBoolean(applicationConstant.apiDebug)) {
            return true;
        }
        if (uri.contains("/api")) {
            // todo probe 指定某些api 不走鉴权
            String method = request.getMethod();
            String date = request.getHeader("Auth-Date");
            String authHeader = request.getHeader("API-AUTH");
            String clientId = request.getHeader("client-id");
            String auth = AuthUtil.getAuthorization(uri, method, date, clientId, "msf");
            if (StringUtil.equalsIgnoreCase(authHeader, auth)) {
                return true;
            } else {
                throw new RemoteCallException(new Error(CommonErrorCode.UNAUTHORIZED.getCode(), uri, "API认证失败")
                        , CommonErrorCode.UNAUTHORIZED.getStatus());
            }
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private String getUri(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceAll(";.*", "");
        return uri.substring(request.getContextPath().length());
    }


}
