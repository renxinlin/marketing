package com.jgw.supercodeplatform.marketing.common.util.ip;

public class IPEntity {
	private String nation;   //国家:0级地址
	private String province; //省:0级地址
	private String city;     //市:1级地址
	private String region;   //区:2级地址
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "IPEntity [nation=" + nation + ", province=" + province + ", city=" + city + ", region=" + region + "]";
	}
	
}
