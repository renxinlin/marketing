package com.jgw.supercodeplatform.marketing.vo.activity;

public class H5LoginVO {
	/**
	 *
	 */
	private Long memberId;//返回用户表主键id

	private String memberName;//用户姓名
	
	private int registered;//1已注册不需要再去完善信息 0需要去完善信息


	private String organizationId;
	private String organizationName;

	private String  customerName;

	private String customerId;

    private Integer haveIntegral; //  会员积分

    private String mobile;//手机

	private String wechatHeadImgUrl;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 参见枚举值
	 */
	private Byte memberType;

	public Byte getMemberType() {
		return memberType;
	}

	public void setMemberType(Byte memberType) {
		this.memberType = memberType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getWechatHeadImgUrl() {
		return wechatHeadImgUrl;
	}

	public void setWechatHeadImgUrl(String wechatHeadImgUrl) {
		this.wechatHeadImgUrl = wechatHeadImgUrl;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public int getRegistered() {
		return registered;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Integer getHaveIntegral() {
		return haveIntegral;
	}

	public void setHaveIntegral(Integer haveIntegral) {
		this.haveIntegral = haveIntegral;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
