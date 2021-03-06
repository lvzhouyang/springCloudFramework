package com.hearglobal.msp.core.mvc.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 自定义拦截器配置
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    private ApiAuthInterceptor apiAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new DefaultAclInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(apiAuthInterceptor).addPathPatterns("/api/**");
        super.addInterceptors(registry);
    }
}
