package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("红包记录")
public class AmoutRecord {
    @ApiModelProperty("红包金额字符串")
    private String salerAmountStr;
    @ApiModelProperty("红包金额")
    private Float salerAmount;
    @ApiModelProperty("时间")
    private String createDate;
    @ApiModelProperty("码")
    private String outerCodeId;
    @ApiModelProperty("产品名称")
    private String productName;

}
