package com.jgw.supercodeplatform.marketing.exception;

/** 码平台异常根类
 * @author Created by jgw136 on 2018/03/14.
 */
public class CodePlatformException extends Exception{

    private Object result;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public CodePlatformException() {
    }

    public CodePlatformException(String message) {
        super(message);
    }

    public CodePlatformException(String message,Object result) {
        super(message);
        this.result = result;
    }

    public CodePlatformException(String message,Integer status) {
        super(message);
        this.status = status;
    }

    public CodePlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodePlatformException(Throwable cause) {
        super(cause);
    }

    public CodePlatformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
