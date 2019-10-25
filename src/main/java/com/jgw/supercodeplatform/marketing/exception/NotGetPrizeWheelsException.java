package com.jgw.supercodeplatform.marketing.exception;

/**
 * 会员用户获取异常，H5通过该异常跳转登录页
 */
public class NotGetPrizeWheelsException extends RuntimeException {

    public NotGetPrizeWheelsException() {
    }

    public NotGetPrizeWheelsException(String message) {
        super(message);
    }

    public NotGetPrizeWheelsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotGetPrizeWheelsException(Throwable cause) {
        super(cause);
    }

    public NotGetPrizeWheelsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
