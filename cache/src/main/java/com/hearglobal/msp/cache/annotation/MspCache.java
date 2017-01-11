package com.hearglobal.msp.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* 缓存方法
* @author lvzhouyang
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MspCache {

  /**
   * 在缓存中保留的时间，单位 秒<br/>
   * 0 表示无限期缓存<br/>
   * 负数 表示不缓存
   * @return
   */
  int keepSecond() default 60 * 60 * 24;

  String cacheKey() default "";

  boolean reCache() default false;
}
