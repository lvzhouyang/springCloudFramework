package com.hearglobal.msp.core.mvc.web.interceptor;

import com.hearglobal.msp.core.context.ApplicationConstant;
import com.hearglobal.security.AuthUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * The type Api auth interceptor.
 * api调用 添加验证头
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.19
 */
@Component
public class ApiAuthHeaderInterceptor implements RequestInterceptor {

    @Resource
    private ApplicationConstant applicationConstant;

    /**
     * Apply.
     *
     * @param template the template
     * @since 2017.04.19
     */
    @Override
    public void apply(RequestTemplate template) {
        if (!template.url().contains("/api")) {
            return;
        }
        String date = System.currentTimeMillis() + "";
        template.header("Auth-Date", date);
        template.header("client-id", applicationConstant.applicationName);
        template.header("API-AUTH", AuthUtil.getAuthorization(template.url(), template.method(), date, applicationConstant.applicationName, "msf"));
    }

}
