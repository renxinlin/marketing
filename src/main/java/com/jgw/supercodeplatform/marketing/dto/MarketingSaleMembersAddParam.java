package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MarketingSaleMembersAddParam {




    @ApiModelProperty(value = "手机",required=true)
    private String mobile;//手机

    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名

    /** 省市区前端编码 */
    private String pCCcode;

    private List<CustomerInfo> customer;

    private String verificationCode;

}
