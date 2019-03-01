package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员编辑")
public class MarketingMembersUpdateParam {

    @ApiModelProperty(value = "用户Id",required=true)
    private String userId;//用户Id

    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名

    @ApiModelProperty(value = "性别")
    private String sex;//性别

    @ApiModelProperty(value = "生日")
    private String birthday;//生日

    @ApiModelProperty(value = "市编码")
    private String cityCode;//市编码

    @ApiModelProperty(value = "门店名称")
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店编码")
    private String customerCode;//门店编码

    @ApiModelProperty(value = "宝宝生日")
    private String babyBirthday;//宝宝生日

    @ApiModelProperty(value = "微信id号")
    private String openid;//微信id号

    @ApiModelProperty(value = "微信昵称")
    private String wxName;//微信昵称
    
    public MarketingMembersUpdateParam() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
