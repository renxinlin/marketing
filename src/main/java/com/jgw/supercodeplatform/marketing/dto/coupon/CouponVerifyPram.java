package com.jgw.supercodeplatform.marketing.dto.coupon;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("抵扣券核销")
public class CouponVerifyPram {
	@NotBlank(message = "memberPhone不能为空")
	@ApiModelProperty(value = "用户会员手机号", required=true)
	private String memberPhone;
	@NotBlank(message = "couponCode不能为空")
	@ApiModelProperty(value = "抵扣券码", required=true)
	private String couponCode;

}
