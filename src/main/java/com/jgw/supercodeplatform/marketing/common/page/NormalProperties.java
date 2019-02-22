package com.jgw.supercodeplatform.marketing.common.page;

public class NormalProperties {
	//验证码
	public static final long PHONE_CODE_TIMEOUT = 120l ;//短信验证码过期时间
	public static final long MAIL_CODE_TIMEOUT = 3600L ;//短信验证码过期时间

	//分页
	public static final int DEFAULT_PAGE_COUNT = 10;//默认每页记录数
	public static final int DEFAULT_CURRENT_PAGE = 1;//默认当前页
	
	//导出
	public static final String EXPORT_ALL = "EXPORT_ALL";//导出全部
	public static final String EXPORT_SELECT = "EXPORT_SELECT";//导出选中
	
	//类型
	public static final String SYSTEM = "SYSTEM";//系统
	public static final String PLATFORM = "PLATFORM";//平台
	public static final String ORG = "ORG";//组织
	
	//工作表名字
	public static final String EXPORT_ORG = "组织信息";//导出组织
	public static final String EXPORT_DEP = "部门信息";//导出部门
	public static final String EXPORT_ROlE = "角色信息";//导出角色
	public static final String EXPORT_EMP = "员工信息";//导出员工
	public static final String EXPORT_EMP_APPLY = "员工申请记录信息";//导出员工申请记录
	public static final String EXPORT_MATERIAL = "原料信息";//导出原料信息
	
	public static final String EXPORT_CODE_GENERATE = "生码信息";//导出原料信息
	
	//发送短信运营商配置
	public static final String PHONE_OPERATORC_CODE = "00001";//
	public static final String PHONE_OPERATORC_NAME = "上海创蓝文化传播有限公司";//
}
