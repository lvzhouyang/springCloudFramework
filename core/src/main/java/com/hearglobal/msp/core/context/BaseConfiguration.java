package com.hearglobal.msp.core.context;

import com.hearglobal.msp.core.mvc.AppErrorController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * The type Base configuration.
 * 定义Spring cloud基础支持的配置
 * 定义扫包范围
 * 定义容器常量存储
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients("com.hearglobal.**.service")
@ComponentScan(basePackages = {"com.hearglobal.**.web","com.hearglobal.**.service"
        ,"com.hearglobal.**.config","com.hearglobal.**.mapper"
        ,"com.hearglobal.**.action","com.hearglobal.**.manager"
        ,"com.hearglobal.**.remote","com.**"}, excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=AppErrorController.class)})
public class BaseConfiguration {

    @Bean
    public ApplicationConstant applicationConstant() {
        return new ApplicationConstant();
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}