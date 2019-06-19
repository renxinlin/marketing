package com.jgw.supercodeplatform.marketing.vo.coupon;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户优惠券")
public class CouponPageVo {
	
	@ApiModelProperty("用户抵扣券id")
	private Long id;
	@ApiModelProperty("用户抵扣券码")
	private String couponCode;
	@ApiModelProperty("用户抵扣券金额")
	private Double couponAmount;
	@ApiModelProperty(value = "抵扣开始时间", dataType = "java.lang.String")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deductionStartDate;
	@ApiModelProperty(value = "抵扣结束时间", dataType = "java.lang.String")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deductionEndDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Date getDeductionStartDate() {
		return deductionStartDate;
	}

	public void setDeductionStartDate(Date deductionStartDate) {
		this.deductionStartDate = deductionStartDate;
	}

	public Date getDeductionEndDate() {
		return deductionEndDate;
	}

	public void setDeductionEndDate(Date deductionEndDate) {
		this.deductionEndDate = deductionEndDate;
	}
	
}
