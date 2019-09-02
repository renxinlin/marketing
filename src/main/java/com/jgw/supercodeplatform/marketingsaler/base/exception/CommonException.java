package com.jgw.supercodeplatform.marketingsaler.base.exception;

import com.jgw.supercodeplatform.exception.SuperCodeException;

public class CommonException extends SuperCodeException {

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, int status) {
        super(message,status);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
