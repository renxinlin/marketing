package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员注册")
public class MarketingMembersAddParam {

    @ApiModelProperty(value = "用户Id")
    private String userId;//用户Id

    @ApiModelProperty(value = "微信id号")
    private String openid;//微信id号

    @ApiModelProperty(value = "微信昵称")
    private String wxName;//微信昵称

    @ApiModelProperty(value = "手机",required=true)
    private String mobile;//手机

    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名

    @ApiModelProperty(value = "性别")
    private String sex;//性别

    @ApiModelProperty(value = "生日")
    private String birthday;//生日

    @ApiModelProperty(value = "组织Id",required=true)
    private String organizationId;//组织Id

    @ApiModelProperty(value = "门店名称",required=true)
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店编码",required=true)
    private String customerId;//门店编码

    @ApiModelProperty(value = "宝宝生日")
    private String babyBirthday;//宝宝生日
    
    @ApiModelProperty(value = "省市区及编码字段")
    private String pCCcode;
    
    @ApiModelProperty(value = "是否停用",hidden=true)
    private Integer state;

    @ApiModelProperty(value = "手机验证码")
    private  String verificationCode;


    @ApiModelProperty(value = "设备类型 1微信2 支付宝3app 4 浏览器5 qq6其他")
    private  Byte deviceType;

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

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

    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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

	public String getpCCcode() {
		return pCCcode;
	}

	public void setpCCcode(String pCCcode) {
		this.pCCcode = pCCcode;
	}
    
}
