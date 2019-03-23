package com.jgw.supercodeplatform.marketing.constants;

public interface WechatConstants {
    
	//微信授权获取token接口地址
	public static String AUTH_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";

	//微信授权获取用户基本信息接口
    public static String USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo";
    

	//微信企业付款到零钱接口
    public static String ORGANIZATION_PAY_CHANGE_Suffix_URL="/mmpaymkttransfers/promotion/transfers";
    
   //基础平台发送手机验证码接口
    public static String SEND_PHONE_CODE_URL="/sms/send/phone/code";
    
    //码管理获取码管理批次信息接口
    public static String CODEMANAGER_GET_BATCH_CODE_INFO_URL="/code/relation/getBatchInfo";
    
    //码管理绑定url到批次接口
    public static String CODEMANAGER_BIND_BATCH_TO_URL="/code/sbatchUrl/addSbatchUrl";
    
    //码管理跳转到营销系统路径
    public static String SCAN_CODE_JUMP_URL="/marketing/front/scan/";
    
    //获取accesstoken
    public static String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    
  //获取accesstoken
    public static String WECHAT_USER_INFO="https://api.weixin.qq.com/cgi-bin/user/info";
    
    
    
}
