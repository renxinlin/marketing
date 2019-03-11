package com.jgw.supercodeplatform.marketing.constants;

public interface WechatConstants {
    
	//微信授权获取access_token接口地址
	public static String AUTH_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";

	//微信授权获取用户基本信息接口
    public static String USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo";
    

	//微信企业付款到零钱接口
    public static String ORGANIZATION_PAY_CHANGE_Suffix_URL="/mmpaymkttransfers/promotion/transfers";
    
  //微信企业付款到零钱接口
    public static String SEND_PHONE_CODE_URL="/sms/send/phone/code";
    
    //微信企业付款到零钱接口
    public static String CODEMANAGER_GET_BATCH_CODE_INFO_URL="/code/relation/getBatchInfo";
}
