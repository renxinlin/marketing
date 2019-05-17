package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerInfo {
    @ApiModelProperty(value = "门店名称",required=true)
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店编码",required=true)
    private String customerId;//门店编码
}
