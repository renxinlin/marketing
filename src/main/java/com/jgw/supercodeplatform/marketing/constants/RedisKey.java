package com.jgw.supercodeplatform.marketing.constants;

/**
 * 统一的redis的key值管理类
 *
 * @author liujianqiang
 * @date 2018年9月5日
 */
public interface RedisKey {
	
	 public final static String phone_code_prefix="marketing:h5_phone_code:";
	 
	 public final static String organizationId_prefix="marketing:organizationId:";
	 
	 public final static String ACCESS_TOKEN_prefix="marketing:access_token:";
	 
	 public final static String ACTIVITY_PREVIEW_PREFIX="marketing:activity_preview:";

	 public final static String ACTIVITY_PLATFORM_PREVIEW_PREFIX = "marketing:platform_preview:";
}
