package com.jgw.supercodeplatform.marketing.dto.coupon;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("抵扣券领取")
public class CouponObtainParam {

	@NotBlank(message = "productId不能为空")
	@ApiModelProperty(value = "产品ID", required=true)
	private String productId;
	@NotBlank(message = "codeTypeId不能为空")
	@ApiModelProperty(value = "码制ID", required=true)
	private String codeTypeId;
	@NotBlank(message = "outerCodeId不能为空")
	@ApiModelProperty(value = "外码", required=true)
	private String outerCodeId;
	@NotBlank(message = "productBatchId不能为空")
	@ApiModelProperty(value = "产品批次ID", required=true)
	private String productBatchId;
	
}
