package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingMembers {

    private Long id;//序号
    private String openid;//微信id号
    private String wxName;//微信id号
    private String mobile;//手机
    private String userId;//用户Id
    private String userName;//用户姓名
    private String sex;//性别
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
    private Byte isRegistered;//是否已注册(1、表示已注册，0 表示未注册)
    private String pCCcode;
    private Integer haveIntegral;

    public Integer getHaveIntegral() {
        return haveIntegral;
    }

    public void setHaveIntegral(Integer haveIntegral) {
        this.haveIntegral = haveIntegral;
    }

    public MarketingMembers() {
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
}
