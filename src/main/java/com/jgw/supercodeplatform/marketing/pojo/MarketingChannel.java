package com.jgw.supercodeplatform.marketing.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 渠道实体类
 * @author czm
 *
 */
public class MarketingChannel {



	@ApiModelProperty(value = "Id")
	private Long id;
	@ApiModelProperty(value = "经销商或门店名称")
	private String customerName;// 经销商或门店名称
	@ApiModelProperty(value = " 客户唯一编码")
	private String customerId;// 客户唯一编码
	@ApiModelProperty(value = "客户类型 0-渠道经销；1-门店")
	private Byte customerType;// 客户类型 0-渠道经销；1-门店
	@ApiModelProperty(value = "父类类型 0-渠道经销；1-门店；2公司本部即组织")
	private Byte customerSuperiorType;// 父类类型 0-渠道经销；1-门店；2公司本部即组织
	@ApiModelProperty(value = "父类唯一编码")
	private String customerSuperior;// 父类唯一编码
	@ApiModelProperty(value = "活动设置主键ID")
	private Long activitySetId;//活动设置主键ID
	@ApiModelProperty(value = "下级门店参数")
	private List<MarketingChannel> children;// 子节点


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActivitySetId() {
		return activitySetId;
	}

	public void setActivitySetId(Long activitySetId) {
		this.activitySetId = activitySetId;
	}

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

	public void setChildren(List<MarketingChannel> children) {
		this.children = children;
	}

	public List<MarketingChannel> getChildren() {
		return children;
	}
}
