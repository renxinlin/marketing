package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员注册")
public class MarketingMembersAddParam {

    @ApiModelProperty(value = "用户Id")
    private String userId;//用户Id

    @ApiModelProperty(value = "微信id号",required=true)
    private String openid;//微信id号

    @ApiModelProperty(value = "微信昵称",required=true)
    private String wxName;//微信昵称

    @ApiModelProperty(value = "手机",required=true)
    private String mobile;//手机

    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名

    @ApiModelProperty(value = "性别")
    private String sex;//性别

    @ApiModelProperty(value = "生日")
    private String birthday;//生日

    @ApiModelProperty(value = "省编码")
    private String provinceCode;//省编码

    @ApiModelProperty(value = "县编码")
    private String countyCode;//县编码

    @ApiModelProperty(value = "市编码",required=true)
    private String cityCode;//市编码

    @ApiModelProperty(value = "省名称")
    private String provinceName;//省名称

    @ApiModelProperty(value = "县名称")
    private String countyName;//县名称

    @ApiModelProperty(value = "市名称")
    private String cityName;//市名称

    @ApiModelProperty(value = "组织Id",required=true)
    private String organizationId;//组织Id

    @ApiModelProperty(value = "组织全名",required=true)
    private String organizationFullName;//组织全名

    @ApiModelProperty(value = "门店名称",required=true)
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店编码",required=true)
    private String customerCode;//门店编码

    @ApiModelProperty(value = "宝宝生日")
    private String babyBirthday;//宝宝生日

    @ApiModelProperty(value = "是否已注册(1、表示已注册，0 表示未注册)")
    private Byte isRegistered;//是否已注册(1、表示已注册，0 表示未注册)


    public MarketingMembersAddParam() {
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(String babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Byte getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Byte isRegistered) {
        this.isRegistered = isRegistered;
    }
}
