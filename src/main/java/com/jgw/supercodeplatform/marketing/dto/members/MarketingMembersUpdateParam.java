package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员编辑")
public class MarketingMembersUpdateParam {
    @ApiModelProperty(value = "id",required=true)
    private Long id;//用户Id
    
    @ApiModelProperty(value = "手机")
    private String mobile;//手机

    @ApiModelProperty(value = "用户Id",required=true)
    private String userId;//用户Id

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

    @ApiModelProperty(value = "市编码")
    private String cityCode;//市编码

    @ApiModelProperty(value = "省名称")
    private String provinceName;//省名称

    @ApiModelProperty(value = "县名称")
    private String countyName;//县名称

    @ApiModelProperty(value = "市名称")
    private String cityName;//市名称

    @ApiModelProperty(value = "状态(1、 表示正常，0 表示下线)")
    private Byte state;//状态(1、 表示正常，0 表示下线)

    @ApiModelProperty(value = "组织Id")
    private String organizationId;//组织Id

    @ApiModelProperty(value = "门店名称")
    private String customerName;//门店名称

    @ApiModelProperty(value = "门店编码")
    private String customerId;//门店编码

    @ApiModelProperty(value = "宝宝生日")
    private String babyBirthday;//宝宝生日

    @ApiModelProperty(value = "是否已注册(1、表示已注册，0 表示未注册)")
    private Byte isRegistered;//是否已注册(1、表示已注册，0 表示未注册)

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Byte isRegistered) {
        this.isRegistered = isRegistered;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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
    
}
