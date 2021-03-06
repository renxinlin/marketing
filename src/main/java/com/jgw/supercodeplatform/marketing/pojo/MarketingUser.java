package com.jgw.supercodeplatform.marketing.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@TableName("marketing_user")
public class MarketingUser {
    /** 序号 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 微信昵称*/
    private String wxName;
    /** 微信id号*/
    private String openid;

    /** 手机 */
    private String mobile;

    /** 用户Id */
    private String userId;

    /** 用户姓名 */
    private String userName;

    /** 性别1女0男 */
    private String sex;

    /** 生日 */
    private Date birthday;

    /** 省编码 */
    private String provinceCode;

    /** 县编码 */
    private String countyCode;

    /** 市编码 */
    private String cityCode;

    /** 省名称 */
    private String provinceName;

    /** 县名称 */
    private String countyName;

    /** 市名称 */
    private String cityName;

    /** 组织Id */
    private String organizationId;

    /** 建立日期 */
    private Date createDate;

    /** 修改日期 */
    private Date updateDate;

    /** 门店名称 */
    private String customerName;

    /** 门店编码 */
    private String customerId;

    /** 省市区前端编码 */
    private String pCCcode;

    @TableField(exist = false)
    private String codeStr;

    /** 默认1导购员,其他员工等 */
    private Byte memberType;

    /** 用户状态(1、 待审核，2 停用3启用)导购员状态 */
    private Byte state;

    @TableField(exist = false)
    private String stateStr;

    /** 扫码设备类型 */
    private Byte deviceType;

    private Integer haveIntegral;
    /** 累计积分*/
    private Integer totalIntegral;
    /** 来源3、H5 4、系统后台*/
    private Byte source;

    @TableField(exist=false)
    private String sourceStr;
    /**
     * 2.0登录用户名
     */
    private String loginName;
    /**
     * 2.0登录用户密码
     */
    private String password;

    private String wechatHeadImgUrl;
    /**
     * 0为3.0原会员1为2.0迁移会员
     */
    private Integer version;

    @TableField(exist=false)
    //导出使用 无业务需求
    private String address;

    /**
     * 机构类型
     */
    private Byte mechanismType;

    @TableField(exist=false)
    private String mechanismTypeStr;

    @ApiModelProperty(value = "是否已经绑定账号密码")
    private Byte binding;
    @ApiModelProperty(value = "2.0迁移的手机号")
    private String phone;

    public Integer getHaveIntegral() {
        return haveIntegral;
    }

    public void setHaveIntegral(Integer haveIntegral) {
        this.haveIntegral = haveIntegral;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
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

    public String getpCCcode() {
        return pCCcode;
    }

    public void setpCCcode(String pCCcode) {
        this.pCCcode = pCCcode;
    }

    public Byte getMemberType() {
        return memberType;
    }

    public void setMemberType(Byte memberType) {
        this.memberType = memberType;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
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

    public Integer getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(Integer totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
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


    public String getWechatHeadImgUrl() {
        return wechatHeadImgUrl;
    }

    public void setWechatHeadImgUrl(String wechatHeadImgUrl) {
        this.wechatHeadImgUrl = wechatHeadImgUrl;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    public Byte getMechanismType() {
        return mechanismType;
    }

    public void setMechanismType(Byte mechanismType) {
        this.mechanismType = mechanismType;
    }

    public String getMechanismTypeStr() {
        return mechanismTypeStr;
    }

    public void setMechanismTypeStr(String mechanismTypeStr) {
        this.mechanismTypeStr = mechanismTypeStr;
    }

    public String getSourceStr() {
        return sourceStr;
    }

    public void setSourceStr(String sourceStr) {
        this.sourceStr = sourceStr;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
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

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}