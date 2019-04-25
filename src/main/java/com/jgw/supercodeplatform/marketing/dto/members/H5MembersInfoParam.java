package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("H5会员信息")
public class H5MembersInfoParam {
    @ApiModelProperty("会员ID")
    private Long id;//序号
    @ApiModelProperty("微信id号")
    private String openid;//微信id号
    @ApiModelProperty("手机")
    private String mobile;//手机
    @ApiModelProperty("用户Id")
    private String userId;//用户Id
    @ApiModelProperty("用户姓名")
    private String userName;//用户姓名
    @ApiModelProperty("性别")
    private String sex;//性别
    @ApiModelProperty("生日")
    private String birthday;//生日
    @ApiModelProperty("注册时间")
    private String registDate;//注册时间
    @ApiModelProperty("注册时间")
    private Byte state;//状态(1、 表示正常，0 表示下线)
    @ApiModelProperty("建立日期")
    private String createDate;//建立日期
    @ApiModelProperty("修改日期")
    private String updateDate;//修改日期
    @ApiModelProperty("门店名称")
    private String customerName;//门店名称
    @ApiModelProperty("门店编码")
    private String customerId;//门店编码

    @ApiModelProperty("最新一次积分领取时间")
    private Date integralReceiveDate; // 最新一次积分领取时间
    @ApiModelProperty("wechatHeadImgUrl")
    private String wechatHeadImgUrl;
    
    public Date getIntegralReceiveDate() {
        return integralReceiveDate;
    }

    public String getWechatHeadImgUrl() {
		return wechatHeadImgUrl;
	}

	public void setWechatHeadImgUrl(String wechatHeadImgUrl) {
		this.wechatHeadImgUrl = wechatHeadImgUrl;
	}



	public void setIntegralReceiveDate(Date integralReceiveDate) {
        this.integralReceiveDate = integralReceiveDate;
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


    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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
}
