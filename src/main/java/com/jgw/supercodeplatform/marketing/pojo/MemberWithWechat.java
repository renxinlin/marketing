package com.jgw.supercodeplatform.marketing.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class MemberWithWechat {

    private Long memberId;//序号
    private Long wxMemberId;
    private String mobile;//手机
    private String userId;//用户Id
    private String userName;//用户姓名
    private Byte sex;//性别
    private String birthday;//生日
    private String provinceCode;// 省编码
    private String provinceName;// 省名
    private String cityCode;
    private String cityName;
    private String countyCode;
    private String countyName;
    private String registDate;//注册时间
    private Byte state;//状态(1、 表示正常，0 表示下线)
    private String organizationId;//组织Id
    private String OrganizationFullName;
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
    private Date integralReceiveDate; // 最新一次积分领取时间
    private Byte userSource;// 注册来源1招募会员
    private Byte deviceType;// 设备来源
    private String wxName;
    private String openid;
    private String wechatHeadImgUrl;
    //是否是用的甲骨文公众号登录,是否为某企业使用的甲骨文公众号1：是，0:否
    private byte jgwType;
    private String appid;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setWxMemberId(Long wxMemberId) {
        this.wxMemberId = wxMemberId;
    }

    public Long getWxMemberId() {
        return wxMemberId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public Byte getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Byte isRegistered) {
        this.isRegistered = isRegistered;
    }

    public String getpCCcode() {
        return pCCcode;
    }

    public void setpCCcode(String pCCcode) {
        this.pCCcode = pCCcode;
    }

    public Integer getHaveIntegral() {
        return haveIntegral;
    }

    public void setHaveIntegral(Integer haveIntegral) {
        this.haveIntegral = haveIntegral;
    }

    public Byte getMemberType() {
        return memberType;
    }

    public void setMemberType(Byte memberType) {
        this.memberType = memberType;
    }

    public Date getIntegralReceiveDate() {
        return integralReceiveDate;
    }

    public void setIntegralReceiveDate(Date integralReceiveDate) {
        this.integralReceiveDate = integralReceiveDate;
    }

    public Byte getUserSource() {
        return userSource;
    }

    public void setUserSource(Byte userSource) {
        this.userSource = userSource;
    }

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getWechatHeadImgUrl() {
        return wechatHeadImgUrl;
    }

    public void setWechatHeadImgUrl(String wechatHeadImgUrl) {
        this.wechatHeadImgUrl = wechatHeadImgUrl;
    }

    public byte getJgwType() {
        return jgwType;
    }

    public void setJgwType(byte jgwType) {
        this.jgwType = jgwType;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxName() {
        return wxName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        OrganizationFullName = organizationFullName;
    }

    public String getOrganizationFullName() {
        return OrganizationFullName;
    }
}
