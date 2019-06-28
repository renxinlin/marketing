package com.jgw.supercodeplatform.marketing.dto.coupon;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CouponVerifyPram {
	@NotBlank(message = "memberPhone不能为空")
	private String memberPhone;
	@NotBlank(message = "couponCode不能为空")
	private String couponCode;

}
