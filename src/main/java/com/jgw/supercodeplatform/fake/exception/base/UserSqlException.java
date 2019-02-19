package com.jgw.supercodeplatform.fake.exception.base;

/** 用户相关sql语句错误异常
 * Created by corbe on 2018/9/4.
 */
public class UserSqlException extends Exception{

    private int status;

    public int getStatus() {
        return status;
    }

    public UserSqlException() {
    }

    public UserSqlException(String message) {
        super(message);
    }

    public UserSqlException(String message, int status) {
        super(message);
        this.status = status;
    }

    public UserSqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
