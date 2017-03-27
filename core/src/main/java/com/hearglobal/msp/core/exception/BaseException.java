package com.hearglobal.msp.core.exception;


/**
 * The type Base exception.
 * 异常基类
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
