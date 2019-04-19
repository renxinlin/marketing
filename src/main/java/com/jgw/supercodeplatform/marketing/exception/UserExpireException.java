package com.jgw.supercodeplatform.marketing.exception;

/**
 * 会员用户获取异常，H5通过该异常跳转登录页
 */
public class UserExpireException extends Exception {

    public UserExpireException() {
    }

    public UserExpireException(String message) {
        super(message);
    }

    public UserExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExpireException(Throwable cause) {
        super(cause);
    }

    public UserExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
