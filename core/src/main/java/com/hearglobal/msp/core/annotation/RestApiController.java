package com.hearglobal.msp.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Created by ccg on 2017/1/4.
 */

//源注解 声明注解范围  要不写就不认识自定义的注解了
//声明的 type  可用于 Class, interface (including annotation type), or enum declaration
@Target({ElementType.TYPE})
//保留    保留策略：运行时保留  这类注解会保留在三个阶段 source 源代码  class 编译文件  runtime 运行时
@Retention(RetentionPolicy.RUNTIME)
//文档  所有的注解文档应该被 javadoc 记录的
@Documented
@RestController
@RequestMapping
public @interface RestApiController {
    String name() default "";
    
    @AliasFor("path")
    String[] value() default {"/api"};
    
    @AliasFor("value")
    String[] path() default {"/api"};
    
    RequestMethod[] method() default {};
    
    String[] params() default {};
    
    String[] headers() default {};
    
    String[] consumes() default {};
    
    String[] produces() default {};
}
