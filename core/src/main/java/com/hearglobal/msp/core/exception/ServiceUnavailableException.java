package com.hearglobal.msp.core.exception;


import com.hearglobal.msp.api.CommonErrorCode;
import com.hearglobal.msp.api.ErrorCode;

/**
 * The type Service unavailable exception.
 * 服务不可用异常
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public class ServiceUnavailableException extends AppBusinessException {

    private static final ErrorCode ERROR_CODE = CommonErrorCode.SERVICE_UNAVAILABLE;

    public ServiceUnavailableException(String message) {
        super(ERROR_CODE.getCode(), ERROR_CODE.getStatus(), " 远程服务不可用: " + message);
    }

}
