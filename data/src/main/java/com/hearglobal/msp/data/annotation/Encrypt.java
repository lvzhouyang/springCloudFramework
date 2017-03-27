package com.hearglobal.msp.data.annotation;

import java.lang.annotation.*;

/**
 * 对实体属性进行标示
 * 对相应属性会在数据库插入、更新、查找时 进行加密/解密操作
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented//说明该注解将被包含在javadoc中
public @interface Encrypt {

}
