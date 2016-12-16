package com.hearglobal.msp.core.context;

import org.springframework.beans.factory.annotation.Value;

/**
 * 系统级常量
 */
public class ApplicationConstant {

    @Value("${spring.application.name}")
    public String applicationName;

}
