package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MarketingSaleMembersUpdateParam {


    private Long id;

    @ApiModelProperty(value = "手机",required=true)
    private String mobile;//手机

    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名

    /** 省市区前端编码 */
    private String pCCcode;

//    private List<CustomerInfo> customer;

    @ApiModelProperty(value = "门店名称",required=true)
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店ID",required=true)
    private String customerId;//门店编码


}
