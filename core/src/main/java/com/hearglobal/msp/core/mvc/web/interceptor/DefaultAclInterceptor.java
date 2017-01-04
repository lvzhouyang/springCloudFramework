package com.hearglobal.msp.core.mvc.web.interceptor;

import com.hearglobal.msp.core.context.AppThreadCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lvzhouyang on 16/12/14.
 */
public class DefaultAclInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(DefaultAclInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        AppThreadCache.setStartTime();
        //todo 鉴权服务
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String uri = this.getUri(httpServletRequest);
        if (!uri.endsWith(".ajax") && (uri.indexOf('.') != -1 || uri.contains("/cron/"))) {
            return;
        }
        long time = AppThreadCache.getStartTime();
        if (time > 0) {
            long timeCost = System.currentTimeMillis() - time;
            logger.info("time used, {}, {}ms", httpServletRequest.getRequestURI(), timeCost);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private String getUri(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceAll(";.*", "");
        return uri.substring(request.getContextPath().length());
    }
}
