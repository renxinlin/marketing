package com.jgw.supercodeplatform.marketing.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@TableName("marketing_members")
public class MarketingMembers {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;//序号
    private String wxName;//微信昵称
    private String openid;//微信id号
    private String mobile;//手机
    private String userId;//用户Id
    private String userName;//用户姓名
    private Byte sex;//性别
    @TableField(exist=false)
    private String sexStr;//性别
    private String birthday;//生日
    private String provinceCode;// 省编码
    private String provinceName;// 省名
    private String cityCode;
    private String cityName;
    private String countyCode;
    private String countyName;
    private String registDate;//注册时间
    private Byte state;//状态(1、 表示正常，0 表示下线)
    @TableField(exist=false)
    private String stateStr;
    private String organizationId;//组织Id
    private String newRegisterFlag;//是否新注册的标志(1  表示是，0 表示不是)
    private String createDate;//建立日期
    private String updateDate;//修改日期
    private String customerName;//门店名称
    private String customerId;//门店编码
    private String babyBirthday;//宝宝生日
    private Byte isRegistered;// 是否已完善(1、表示已完善，0 表示未完善)
    private String pCCcode;
    @TableField(exist=false)
    //导出省市区字符串无业务需求
    private String codeStr;
    private Integer haveIntegral; //  会员积分
    private Byte memberType; // 会员类型默认0
    private Date integralReceiveDate; // 最新一次积分领取时间
    private Byte userSource;// 注册来源1招募会员
    private Byte deviceType;// 设备来源
    //下述为2.0迁移
    @ApiModelProperty(value = "2.0登录用户名")
    private String loginName;//2.0登录用户名
    @ApiModelProperty(value = "2.0登录用户密码")
    private String password;//2.0登录用户密码
    @ApiModelProperty(value = "0为3.0原会员1为2.0迁移会员")
    private Byte version;//0为3.0原会员1为2.0迁移会员
    @ApiModelProperty(value = "详细地址")
    private String detailAddress;//详细地址
    @ApiModelProperty(value = "身份证")
    private String iDNumber;//身份证
    @ApiModelProperty(value = "注册途径1、手机积分商城2、PC积分网站3、手机充值H5 4、网站后台 5、手机客户端 6、微信 7、\"外部网站 8、码上淘 9、短信 10、微盟")
    private Byte registrationApproach;//注册途径1、手机积分商城2、PC积分网站3、手机充值H5 4、网站后台 5、手机客户端 6、微信 7、"外部网站 8、码上淘 9、短信 10、微盟

    @TableField(exist=false)
    private String registrationApproachStr;

    @ApiModelProperty(value = "累计积分")
    private Integer totalIntegral;//累计积分
    @ApiModelProperty(value = "会员等级")
    private Byte memberGrade;//会员等级
    @ApiModelProperty(value = "推荐人")
    private Long recommenderId;//推荐人
    @ApiModelProperty(value = "二级推荐人")
    private Long secondLevelRecommenderId;//二级推荐人
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "是否已经绑定账号密码")
    private Byte binding;
    @ApiModelProperty(value = "2.0迁移的手机号")
    private String phone;

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

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public Byte getUserSource() {
        return userSource;
    }

    public void setUserSource(Byte userSource) {
        this.userSource = userSource;
    }

    public Date getIntegralReceiveDate() {
        return integralReceiveDate;
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

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getSex() {
        return sex;
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
    public void setSexStr(String sexStr){
        this.sexStr=sexStr;
    }
    public String getSexStr(){
        return sexStr;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Byte getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(Byte memberGrade) {
        this.memberGrade = memberGrade;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getRecommenderId() {
        return recommenderId;
    }

    public void setRecommenderId(Long recommenderId) {
        this.recommenderId = recommenderId;
    }

    public Long getSecondLevelRecommenderId() {
        return secondLevelRecommenderId;
    }

    public void setSecondLevelRecommenderId(Long secondLevelRecommenderId) {
        this.secondLevelRecommenderId = secondLevelRecommenderId;
    }

    public String getRegistrationApproachStr() {
        return registrationApproachStr;
    }

    public void setRegistrationApproachStr(String registrationApproachStr) {
        this.registrationApproachStr = registrationApproachStr;
    }

    public Byte getBinding() {
        return binding;
    }

    public void setBinding(Byte binding) {
        this.binding = binding;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }


    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }
}
