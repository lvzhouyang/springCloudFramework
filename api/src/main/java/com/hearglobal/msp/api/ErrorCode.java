package com.hearglobal.msp.api;

/**
 * 统一错误码接口
 */
public interface ErrorCode {

    String getCode();

    int getStatus();

    String getMessage();
}
