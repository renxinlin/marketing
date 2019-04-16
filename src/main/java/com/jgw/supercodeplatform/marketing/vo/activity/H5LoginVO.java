package com.jgw.supercodeplatform.marketing.vo.activity;

public class H5LoginVO {
	private Long memberId;//返回用户表主键id

	private int registered;//1已注册不需要再去完善信息 0需要去完善信息
	
	private String organizationName;
	
    private Integer haveIntegral; //  会员积分
    
    private String mobile;//手机
    
    
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
