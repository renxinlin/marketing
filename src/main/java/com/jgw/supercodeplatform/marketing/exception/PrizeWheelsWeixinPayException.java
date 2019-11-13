package com.jgw.supercodeplatform.marketing.exception;

import lombok.Data;

/**
 * 统一业务异常
 */
@Data
public class PrizeWheelsWeixinPayException extends RuntimeException {
    Object object;
    public PrizeWheelsWeixinPayException() {
    }

    public PrizeWheelsWeixinPayException(String message) {
        super(message);
    }


    public PrizeWheelsWeixinPayException(String message, Object o) {
        super(message);
        this.object = o;
    }

    public PrizeWheelsWeixinPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrizeWheelsWeixinPayException(Throwable cause) {
        super(cause);
    }

    public PrizeWheelsWeixinPayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
