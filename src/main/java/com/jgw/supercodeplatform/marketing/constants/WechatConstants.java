package com.jgw.supercodeplatform.marketing.constants;

public interface WechatConstants {
    
	//微信授权获取token接口地址
    public final static String AUTH_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";

	//微信授权获取用户基本信息接口
    public final static String USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo";
    

	//微信企业付款到零钱接口
    public final static String ORGANIZATION_PAY_CHANGE_Suffix_URL="/mmpaymkttransfers/promotion/transfers";
    
    //基础平台发送手机验证码接口
    public final static String SEND_PHONE_CODE_URL="/sms/send/phone/code";
    
    //码管理获取码管理批次信息接口
    //String CODEMANAGER_GET_BATCH_CODE_INFO_URL="/code/relation/getBatchInfo";

    //码管理获取批次信息，不局限与任何一种绑定关系信息接口
    public final static String CODEMANAGER_GET_BATCH_CODE_INFO_URL_WITH_ALL_RELATIONTYPE="/code/relation/getBatchInfoWithoutType";
    
    //码管理绑定url到批次接口
    public final static String CODEMANAGER_BIND_BATCH_TO_URL="/code/sbatchUrl/addSbatchUrl";
    
    /** 删除码绑定关系接口 */
    public final static String CODEMANAGER_DELETE_BATCH_TO_URL="/code/sbatchUrl/delete/one";
    
    //码管理跳转到营销系统路径
    public final static String SCAN_CODE_JUMP_URL="/marketing/front/scan/";

    //码管理跳转到营销系统路径
    public final static String SALER_SCAN_CODE_JUMP_URL="/marketing/front/scan/saler";
    
    //获取accesstoken
    public final static String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    
    //获取信息
    public final static String WECHAT_USER_INFO="https://api.weixin.qq.com/cgi-bin/user/info";

    // 短信发送接口
    public final static String SMS_SEND_PHONE_MESSGAE="/sms/send/phone/message";

    public final static String ORG_LIST_PLATFORM = "/org/list/platform";

    /**
     * 导购登录页
     */
    public final static String SALER_LOGIN_URL ="#/sales/index"  ;
    
    
}
