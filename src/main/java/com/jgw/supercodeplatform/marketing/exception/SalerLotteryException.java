package com.jgw.supercodeplatform.marketing.exception;

/**
 * 导购领取异常失败
 */
public class SalerLotteryException extends Exception {

    public SalerLotteryException() {
    }

    public SalerLotteryException(String message) {
        super(message);
    }

    public SalerLotteryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SalerLotteryException(Throwable cause) {
        super(cause);
    }

    public SalerLotteryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
