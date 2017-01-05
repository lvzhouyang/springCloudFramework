package com.hearglobal.msp.core.mvc.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义拦截器配置
 * Created by lvzhouyang on 16/12/14.
 */
@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new DefaultAclInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
