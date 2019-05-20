package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("注册参数")
public class MarketingSaleMembersAddParam {
    @ApiModelProperty(value = "组织ID",required=true)
    private String organizationId;


    @ApiModelProperty(value = "手机",required=true)
    private String mobile;//手机

    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名

    /** 省市区前端编码 */
    private String pCCcode;

    private List<CustomerInfo> customer;

    private String verificationCode;
    @ApiModelProperty(value = "客户端类型，字符串格式1表示微信2表示非微信")
    private String browerType ;

}
