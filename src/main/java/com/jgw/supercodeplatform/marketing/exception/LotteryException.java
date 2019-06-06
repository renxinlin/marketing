package com.jgw.supercodeplatform.marketing.exception;

import com.jgw.supercodeplatform.exception.SuperCodeException;

/**
 * 导购领取异常失败
 */
public class LotteryException extends SuperCodeException {

	private static final long serialVersionUID = -4749586660272961594L;

	public LotteryException() {
    }

    public LotteryException(String message) {
        super(message);
    }
    
    public LotteryException(String message, int code) {
        super(message, code);
    }

    public LotteryException(String message, Throwable cause) {
        super(message, cause);
    }

}
