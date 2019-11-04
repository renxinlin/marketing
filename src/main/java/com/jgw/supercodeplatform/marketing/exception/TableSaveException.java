package com.jgw.supercodeplatform.marketing.exception;

/**
 * 会员用户获取异常，H5通过该异常跳转登录页
 */
public class TableSaveException extends RuntimeException {

    public TableSaveException() {
    }

    public TableSaveException(String message) {
        super(message);
    }

    public TableSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableSaveException(Throwable cause) {
        super(cause);
    }

    public TableSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
