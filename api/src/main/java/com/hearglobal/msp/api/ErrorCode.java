package com.hearglobal.msp.api;

/**
 * 统一错误码接口
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public interface ErrorCode {

    /**
     * Gets code.
     *
     * @return the code
     */
    String getCode();

    /**
     * Gets status.
     *
     * @return the status
     */
    int getStatus();

    /**
     * Gets message.
     *
     * @return the message
     */
    String getMessage();
}
