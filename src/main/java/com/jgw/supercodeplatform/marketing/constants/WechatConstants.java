package com.jgw.supercodeplatform.marketing.constants;

public interface WechatConstants {
	//微信公众号APPID
	public static String APPID="wx832dbcebd3f0a343";
	
	//微信公众号secret
	public static String secret="0dfac9cd3dae12b6d988baf26a4bfe09";
    
	//微信授权获取access_token接口地址
	public static String AUTH_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";

	//微信授权获取用户基本信息接口
    public static String USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo";
    

	//微信企业付款到零钱接口
    public static String ORGANIZATION_PAY_CHANGE_URL="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
}
