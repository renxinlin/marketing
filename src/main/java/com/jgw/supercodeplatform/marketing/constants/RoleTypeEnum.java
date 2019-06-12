package com.jgw.supercodeplatform.marketing.constants;

public enum RoleTypeEnum {
	MEMBER(0,"会员"),GUIDE(1,"导购");
	
	private int memberType;
	private String desc;
	public int getMemberType() {
		return memberType;
	}
	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	RoleTypeEnum(int memberType, String desc) {
		this.memberType = memberType;
		this.desc = desc;
	}


}
