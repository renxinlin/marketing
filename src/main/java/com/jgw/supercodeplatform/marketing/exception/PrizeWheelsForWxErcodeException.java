package com.jgw.supercodeplatform.marketing.exception;

/**
 * 会员用户获取异常，H5通过该异常跳转登录页
 */
public class PrizeWheelsForWxErcodeException extends RuntimeException {

    public PrizeWheelsForWxErcodeException() {
    }

    public PrizeWheelsForWxErcodeException(String message) {
        super(message);
    }

    public PrizeWheelsForWxErcodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrizeWheelsForWxErcodeException(Throwable cause) {
        super(cause);
    }

    public PrizeWheelsForWxErcodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
