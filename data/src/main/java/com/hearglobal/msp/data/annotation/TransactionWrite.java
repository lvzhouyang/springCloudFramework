package com.hearglobal.msp.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * 取代默认的事务配置,加入自定义的配置参数
 * 可以自定义事务的隔离级别和传播行为等参数 按需加载
 * 设置默认事务超时为5s 超时抛异常 并且异常可以自定义 但是一般设为默认
 * 配置事务的读写性 为可读写
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional
public @interface TransactionWrite {

    //指定事务管理者的名称 默认为"" 可以自己指定

    /**
     * Value string.
     *
     * @return the string
     * @since 2017.03.27
     */
    @AliasFor("transactionManager") String value() default "";

    /**
     * Transaction manager string.
     *
     * @return the string
     * @since 2017.03.27
     */
    @AliasFor("value") String transactionManager() default "";

    //事务传播行为

    /**
     * REQUIRED,默认最常见 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中
     * <p>
     * SUPPORTS,支持当前事务，如果当前没有事务，就以非事务方式执行
     * <p>
     * MANDATORY,使用当前的事务，如果当前没有事务，就抛出异常
     * <p>
     * REQUIRES_NEW,新建事务，如果当前存在事务，把当前事务挂起
     * <p>
     * NOT_SUPPORTED,以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
     * <p>
     * NEVER,以非事务方式执行，如果当前存在事务，则抛出异常
     * <p>
     * NESTED;如果当前存在事务，则在嵌套事务内执行。
     * 如果当前没有事务，则执行与PROPAGATION_REQUIRED类 似的操作
     *
     * @return the propagation
     * @since 2017.03.27
     */
    Propagation propagation() default Propagation.REQUIRED;

    //事务隔离级别

    /**
     * DEFAULT,这是默认值，表示使用底层数据库的默认隔离级别，对于大部分数据库来说，可以看作是READ_COMMITTED
     * <p>
     * READ_UNCOMMITTED,该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据
     * 该级别不能防止脏读，不可重复读和幻读，因此很少使用该隔离级别
     * <p>
     * READ_COMMITTED,该隔离级别表示一个事务只能读取另一个事务已经提交的数据
     * 该级别可以防止脏读，这也是大多数情况下的推荐值
     * <p>
     * REPEATABLE_READ,该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同
     * 该级别可以防止脏读和不可重复读
     * <p>
     * SERIALIZABLE,所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰
     * 也就是说，该级别可以防止脏读、不可重复读以及幻读
     * 但是这将严重影响程序的性能。通常情况下也不会用到该级别
     *
     * @return the isolation
     * @since 2017.03.27
     */
    Isolation isolation() default Isolation.DEFAULT;

    //超时时间

    /**
     * 超时时间我们这里默认为 5s
     *
     * @return the int
     * @since 2017.03.27
     */
    int timeout() default 5;

    //事务读写性

    /**
     * 配置事务的读写特性，是可读的还是读写 默认可读写
     *
     * @return the boolean
     * @since 2017.03.27
     */
    boolean readOnly() default false;

    //一组异常类 遇到会回滚

    /**
     * 默认为空
     *
     * @return the class [ ]
     * @since 2017.03.27
     */
    Class<? extends Throwable>[] rollbackFor() default {};

    //一组异常类的名字 遇到会回滚

    /**
     * 默认为空
     *
     * @return the string [ ]
     * @since 2017.03.27
     */
    String[] rollbackForClassName() default {};

    //一组异常类 遇到不回滚

    /**
     * 默认为空
     *
     * @return the class [ ]
     * @since 2017.03.27
     */
    Class<? extends Throwable>[] noRollbackFor() default {};

    //一组异常类的名字 遇到不回滚

    /**
     * 默认为空
     *
     * @return the string [ ]
     * @since 2017.03.27
     */
    String[] noRollbackForClassName() default {};
}



