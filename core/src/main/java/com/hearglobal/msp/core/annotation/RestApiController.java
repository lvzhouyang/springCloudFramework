package com.hearglobal.msp.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Created by ccg on 2017/1/4.
 *
 * 该注解封装了 RestController 和 RequestMapping
 *
 * 添加默认配置前缀 "/api" 用于远程的API调用
 *
 * 配置可更改在@RestApiController(自定义)
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
public @interface RestApiController {
    String name()                      default "";
    @AliasFor("path") String[] value() default { "/api" };
    @AliasFor("value") String[] path() default { "/api" };
    RequestMethod[] method()           default {};
    String[] params()                  default {};
    String[] headers()                 default {};
    String[] consumes()                default {};
    String[] produces()                default {};
}

