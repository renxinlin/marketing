package com.jgw.supercodeplatform.marketing.enums;

/**
 * 统一的redis的key值管理类
 *
 * @author liujianqiang
 * @date 2018年9月5日
 */
public class RedisKey {
	//注册业务
    public static final String REGISTER_PHONE_CODE = "register:phone:code:";//短信验证码的key值前缀
    public static final String REGISTER_MAIL_CODE = "register:mail:code:";//邮箱验证码的key值前缀

    //登入业务
    public static final String USER_LOGIN = "user:login:";//邮箱验证码的key值前缀
    public static final String USER_LOGIN_IDENTIFYING_CODE = "user:login:identifying:code:";//图形验证码的key值前缀


    //平台管理员邀请用户为管理员的key标识
    public static final String INVITE_TO_PLATFORM_USER = "user:invite:platform:manager:";
    
	public static final String INCR_FAKE_CODE = "fake:incr:fake:code";//防伪码扫码自增值
	public static final String GLOBAL_SYMBOL = "#1";//全局实体类的符号
	
	public static final String SCORE_RULE_CONFIG = "score:rule:config";//

}
