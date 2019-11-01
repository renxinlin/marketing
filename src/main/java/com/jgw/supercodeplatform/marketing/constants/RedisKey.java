package com.jgw.supercodeplatform.marketing.constants;

/**
 * 统一的redis的key值管理类
 *
 * @author liujianqiang
 * @date 2018年9月5日
 */
public interface RedisKey {
	
	 String phone_code_prefix="marketing:h5_phone_code:";
	 
	 String organizationId_prefix="marketing:organizationId:";
	 
	 String ACCESS_TOKEN_prefix="marketing:access_token:";
	 
	 String ACTIVITY_PREVIEW_PREFIX="marketing:activity_preview:";

	 String ACTIVITY_PLATFORM_PREVIEW_PREFIX = "marketing:platform_preview:";

	 String WECHAT_OPENID_INFO = "marketing:openid:info:";
}
