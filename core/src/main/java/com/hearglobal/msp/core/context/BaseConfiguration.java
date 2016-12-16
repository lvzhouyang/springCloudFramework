package com.hearglobal.msp.core.context;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients("com.hearglobal.**.service")
@ComponentScan({"com.hearglobal.**.web","com.hearglobal.**.service"
        ,"com.hearglobal.**.config","com.hearglobal.**.mapper"
        ,"com.hearglobal.**.action","com.hearglobal.**.manager"
        ,"com.hearglobal.**.remote"})
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