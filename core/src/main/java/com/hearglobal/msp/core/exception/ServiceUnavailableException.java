package com.hearglobal.msp.core.exception;


import com.hearglobal.msp.api.CommonErrorCode;
import com.hearglobal.msp.api.ErrorCode;

/**
 *
 */
public class ServiceUnavailableException extends AppBusinessException {

    private static final ErrorCode ERROR_CODE = CommonErrorCode.SERVICE_UNAVAILABLE;

    public ServiceUnavailableException(String message) {
        super(ERROR_CODE.getCode(), ERROR_CODE.getStatus(), " 远程服务不可用: " + message);
    }

}
