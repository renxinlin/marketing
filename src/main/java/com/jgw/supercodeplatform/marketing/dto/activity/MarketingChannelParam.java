package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "活动门店或经销商")
public class MarketingChannelParam {

	@ApiModelProperty(value = "Id")
	private Long id;
	@ApiModelProperty(value = "经销商或门店名称")
	private String customerName;// 经销商或门店名称
	@ApiModelProperty(value = " 客户唯一编码")
	private String customerId;// 桶客户唯一编码



	@ApiModelProperty(value = "客户类型 0-渠道经销；1-门店")
	private Byte customerType;// 客户类型 0-渠道经销；1-门店
	@ApiModelProperty(value = "父类类型 0-渠道经销；1-门店；2公司本部即组织")
	private Byte customerSuperiorType;// 父类类型 0-渠道经销；1-门店；2公司本部即组织
	@ApiModelProperty(value = "父类唯一编码")
	private String customerSuperior;// 父类唯一编码
	@ApiModelProperty(value = "下级门店参数")
	private List<MarketingChannelParam> childrens = new ArrayList<>();

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
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
