package com.hearglobal.msp.core.context;

import com.hearglobal.msp.core.mvc.AppErrorController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

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


}