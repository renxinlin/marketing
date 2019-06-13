package com.jgw.supercodeplatform.marketing.dto.coupon;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("核销人员核销分页")
public class CouponCustmerVerifyPageParam extends DaoSearch {
	@ApiModelProperty(hidden = true)
	private Long verifyMemberId;
	@ApiModelProperty(hidden = true)
	private String verifyCustomerId;

	public Long getVerifyMemberId() {
		return verifyMemberId;
	}

	public void setVerifyMemberId(Long verifyMemberId) {
		this.verifyMemberId = verifyMemberId;
	}

	public String getVerifyCustomerId() {
		return verifyCustomerId;
	}

	public void setVerifyCustomerId(String verifyCustomerId) {
		this.verifyCustomerId = verifyCustomerId;
	}
	
	
}
