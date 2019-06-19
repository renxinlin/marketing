package com.jgw.supercodeplatform.marketing.vo.coupon;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("H5核销记录")
@Data
public class CouponVerifyVo {
	
	@ApiModelProperty("核销金额")
	private Double couponAmount;
	@ApiModelProperty("抵扣券码")
	private String couponCode;
	@ApiModelProperty(value = "核销时间", dataType = "java.lang.String")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date verifyTime;

}
