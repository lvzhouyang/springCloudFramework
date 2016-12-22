package com.hearglobal.msp.core;

import com.hearglobal.msp.core.context.BaseConfiguration;
import com.hearglobal.msp.core.context.WebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * Created by lvzhouyang on 16/12/14.
 * 整合spring boot spring cloud 相关接口
 * 整合基础配置
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@EnableAsync
@Import({BaseConfiguration.class, WebApplication.class})
public @interface CoreWebApplication {
}
