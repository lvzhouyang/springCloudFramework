package com.hearglobal.msp.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存方法返回值的注解
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.03.27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MspCache {

  /**
   * 在缓存中保留的时间，单位 秒<br/>
   * 0 表示无限期缓存<br/>
   * 负数 表示不缓存
   *
   * @return int
   * @since 2017.03.27
   */
  int keepSecond() default 60 * 60 * 24;

  /**
   * 自定义缓存key
   *
   * @return string
   * @since 2017.03.27
   */
  String cacheKey() default "";

  /**
   * 强制刷新
   *
   * @return boolean
   * @since 2017.03.27
   */
  boolean reCache() default false;
}
