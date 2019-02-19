package com.jgw.supercodeplatform.fake.exception.base;

/**
 * 平台全局异常
 * Created by corbett on 2018/9/5.
 */
public class SuperCodeException extends Exception {

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public SuperCodeException() {
    }

    public SuperCodeException(String message) {
        super(message);
    }

    public SuperCodeException(String message, int status) {
        super(message);
        this.status = status;
    }

    public SuperCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
