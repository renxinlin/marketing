package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("注册参数")
public class MarketingSaleMembersAddParam {
    @ApiModelProperty(value = "组织ID",required=true)
    private String organizationId;


    @ApiModelProperty(value = "openId",required=true)
    private String openId;


    @ApiModelProperty(value = "手机",required=true)
    private String mobile;//手机

    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名

    /** 省市区前端编码 */
    private String pCCcode;

    @ApiModelProperty(value = "门店名称",required=true)
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店编码",required=true)
    private String customerId;//门店编码



//    @ApiModelProperty(value = "门店信息")
//    private List<CustomerInfo> customer;
    @ApiModelProperty(value = "用户姓名")
    private String verificationCode;
//    @ApiModelProperty(value = "客户端类型，字符串格式1表示微信2表示非微信")
//    private String browerType ;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getpCCcode() {
        return pCCcode;
    }

    public void setpCCcode(String pCCcode) {
        this.pCCcode = pCCcode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
