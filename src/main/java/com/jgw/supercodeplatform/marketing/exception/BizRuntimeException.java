package com.jgw.supercodeplatform.marketing.exception;

import lombok.Data;

/**
 * 统一业务异常
 */
@Data
public class BizRuntimeException extends RuntimeException {
    Object object;
    public BizRuntimeException() {
    }

    public BizRuntimeException(String message) {
        super(message);
    }


    public BizRuntimeException(String message,Object o) {
        super(message);
        this.object = o;
    }

    public BizRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizRuntimeException(Throwable cause) {
        super(cause);
    }

    public BizRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
