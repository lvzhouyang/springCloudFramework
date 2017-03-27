package com.hearglobal.msp.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 封装带事务的 Service 的只读注解 默认超时5s
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@Service
public @interface MspService {
    /**
     * Value string.
     *
     * @return the string
     * @since 2017.03.27
     */
    @AliasFor("transactionManager")
    String value() default "";

    /**
     * Transaction manager string.
     *
     * @return the string
     * @since 2017.03.27
     */
    @AliasFor("value")
    String transactionManager() default "";

    /**
     * Propagation propagation.
     *
     * @return the propagation
     * @since 2017.03.27
     */
    Propagation propagation() default Propagation.REQUIRED;

    /**
     * Isolation isolation.
     *
     * @return the isolation
     * @since 2017.03.27
     */
    Isolation isolation() default Isolation.DEFAULT;

    /**
     * Timeout int.
     *
     * @return the int
     * @since 2017.03.27
     */
    int timeout() default 5;

    /**
     * Read only boolean.
     *
     * @return the boolean
     * @since 2017.03.27
     */
    boolean readOnly() default true;

    /**
     * Rollback for class [ ].
     *
     * @return the class [ ]
     * @since 2017.03.27
     */
    Class<? extends Throwable>[] rollbackFor() default {};

    /**
     * Rollback for class name string [ ].
     *
     * @return the string [ ]
     * @since 2017.03.27
     */
    String[] rollbackForClassName() default {};

    /**
     * No rollback for class [ ].
     *
     * @return the class [ ]
     * @since 2017.03.27
     */
    Class<? extends Throwable>[] noRollbackFor() default {};

    /**
     * No rollback for class name string [ ].
     *
     * @return the string [ ]
     * @since 2017.03.27
     */
    String[] noRollbackForClassName() default {};
}
