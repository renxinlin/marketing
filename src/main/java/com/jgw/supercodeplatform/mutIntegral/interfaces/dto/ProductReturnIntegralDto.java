package com.jgw.supercodeplatform.mutIntegral.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("退货参数")
public class ProductReturnIntegralDto {

    @NotEmpty(message = "参数全部必填")
    @ApiModelProperty("*会员手机号")
    private String mobile;

    @NotEmpty(message = "参数全部必填")
    @ApiModelProperty("**退货产品码")
    private String outCodeId;

    @NotEmpty(message = "参数全部必填")
    @ApiModelProperty("*退货原因")
    private String reason;
}
