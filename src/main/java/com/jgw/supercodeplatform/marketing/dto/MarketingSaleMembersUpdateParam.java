package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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


    /** 门店类型 */
    @ApiModelProperty("机构类型1总部2子公司3经销商4门店5库房10子门店15地方政府16公司20销售公司25农场31其他")
    private Byte mechanismType;

    @ApiModelProperty(value = "门店名称",required=true)
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店ID",required=true)
    private String customerId;//门店编码



}
