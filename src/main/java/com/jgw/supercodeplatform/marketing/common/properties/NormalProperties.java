package com.jgw.supercodeplatform.marketing.common.properties;

public class NormalProperties {
	// 验证码
	public static final long PHONE_CODE_TIMEOUT = 300L;// 短信验证码过期时间
	public static final long MAIL_CODE_TIMEOUT = 3600L;// 短信验证码过期时间

	// 分页
	public static final int DEFAULT_PAGE_COUNT = 10;// 默认每页记录数
	public static final int DEFAULT_CURRENT_PAGE = 1;// 默认当前页

	// 导出
	public static final String EXPORT_ALL = "EXPORT_ALL";// 导出全部
	public static final String EXPORT_SELECT = "EXPORT_SELECT";// 导出选中

}
