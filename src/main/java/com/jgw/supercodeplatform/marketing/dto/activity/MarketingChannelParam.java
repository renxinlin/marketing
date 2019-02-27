package com.jgw.supercodeplatform.marketing.dto.activity;

import java.util.List;

public class MarketingChannelParam {
	private Long id;

	private String customerName;// 经销商或门店名称

	private String customerCode;// 客户唯一编码

	private Byte customerType;// 客户类型 0-渠道经销；1-门店

	private Byte customerSuperiorType;// 父类类型 0-渠道经销；1-门店；2公司本部即组织

	private String customerSuperior;// 父类唯一编码

	private List<MarketingChannelParam> childrens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Byte getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Byte customerType) {
		this.customerType = customerType;
	}

	public Byte getCustomerSuperiorType() {
		return customerSuperiorType;
	}

	public void setCustomerSuperiorType(Byte customerSuperiorType) {
		this.customerSuperiorType = customerSuperiorType;
	}

	public String getCustomerSuperior() {
		return customerSuperior;
	}

	public void setCustomerSuperior(String customerSuperior) {
		this.customerSuperior = customerSuperior;
	}

	public List<MarketingChannelParam> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<MarketingChannelParam> childrens) {
		this.childrens = childrens;
	}

}
