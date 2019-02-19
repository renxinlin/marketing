package com.jgw.supercodeplatform.marketing.exception;

/**
 * 公共包异常
 * @author liujianqiang
 * @date 2018年4月2日
 */
public class CommonException extends Exception{
	
    private Object result;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message,Object result) {
        super(message);
        this.result = result;
    }

    public CommonException(String message,Integer status) {
        super(message);
        this.status = status;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
