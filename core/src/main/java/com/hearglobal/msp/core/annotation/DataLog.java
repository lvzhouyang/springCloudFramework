/*
 * Copyright (c) 2010-2011 meituan.com
 * All rights reserved.
 * 
 */
package com.hearglobal.msp.core.annotation;

import java.lang.annotation.*;

/**
 *
 * @version 1.0
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataLog {

    /**
     * 字段的名称.也可以当作是字段的解释
     *
     * @return
     */
    String name();

}
