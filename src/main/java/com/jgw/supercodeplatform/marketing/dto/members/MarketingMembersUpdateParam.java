package com.jgw.supercodeplatform.marketing.dto.members;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MarketingMembersUpdateParam {


    private String verificationCode;
    private Long id;//序号
    private String openid;//微信id号
    private String wxName;//微信id号
    private String mobile;//手机
    private String userId;//用户Id
    private String userName;//用户姓名
    private Byte sex;//性别
    private String birthday;//生日
    private String registDate;//注册时间
    private Byte state;//状态(1、 表示正常，0 表示下线)
    private String organizationId;//组织Id
    private String newRegisterFlag;//是否新注册的标志(1  表示是，0 表示不是)
    private String createDate;//建立日期
    private String updateDate;//修改日期
    private String customerName;//门店名称
    private String customerId;//门店编码
    private String babyBirthday;//宝宝生日
    private Byte isRegistered;// 是否已完善(1、表示已完善，0 表示未完善)
    private String pCCcode;
    private Integer haveIntegral; //  会员积分
    private Byte memberType; // 会员类型默认0
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date integralReceiveDate; // 最新一次积分领取时间
    private String wechatHeadImgUrl;

    //如下2.0迁移字段
    @ApiModelProperty(value = "2.0详细地址")
    private String detailAddress;
    @ApiModelProperty(value = "2.0身份证号")
    private String iDNumber;
    @ApiModelProperty(value = "2.0注册途径1、手机积分商城2、PC积分网站3、手机充值H5 4、网站后台 5、手机客户端 6、微信 7、\"外部网站 8、码上淘 9、短信 10、微盟")
    private Byte registrationApproach;//注册途径1、手机积分商城2、PC积分网站3、手机充值H5 4、网站后台 5、手机客户端 6、微信 7、"外部网站 8、码上淘 9、短信 10、微盟
    @ApiModelProperty(value = "2.0累计积分(不可编辑)")
    private Integer totalIntegral;//累计积分



    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

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

    public Byte getMemberType() {
        return memberType;
    }

    public void setMemberType(Byte memberType) {
        this.memberType = memberType;
    }

    public Integer getHaveIntegral() {
        return haveIntegral;
    }

    public void setHaveIntegral(Integer haveIntegral) {
        this.haveIntegral = haveIntegral;
    }

    public String getpCCcode() {
		return pCCcode;
	}

	public void setpCCcode(String pCCcode) {
		this.pCCcode = pCCcode;
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

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
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

    public Byte getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Byte isRegistered) {
        this.isRegistered = isRegistered;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getNewRegisterFlag() {
        return newRegisterFlag;
    }

    public void setNewRegisterFlag(String newRegisterFlag) {
        this.newRegisterFlag = newRegisterFlag;
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

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(String babyBirthday) {
        this.babyBirthday = babyBirthday;
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


    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getiDNumber() {
        return iDNumber;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public Byte getRegistrationApproach() {
        return registrationApproach;
    }

    public void setRegistrationApproach(Byte registrationApproach) {
        this.registrationApproach = registrationApproach;
    }

    public Integer getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(Integer totalIntegral) {
        this.totalIntegral = totalIntegral;
    }
}
