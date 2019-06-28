package com.jgw.supercodeplatform.marketing.dto.coupon;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CouponObtainParam {

	@NotBlank(message = "productId不能为空")
	private String productId;
	@NotBlank(message = "codeTypeId不能为空")
	private String codeTypeId;
	@NotBlank(message = "outerCodeId不能为空")
	private String outerCodeId;
	@NotBlank(message = "productBatchId不能为空")
	private String productBatchId;
	
}
