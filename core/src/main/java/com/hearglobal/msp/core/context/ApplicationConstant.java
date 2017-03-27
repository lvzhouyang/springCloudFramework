package com.hearglobal.msp.core.context;

import org.springframework.beans.factory.annotation.Value;

/**
 * 系统级常量注入
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public class ApplicationConstant {

    @Value("${spring.application.name}")
    public String applicationName;

}
