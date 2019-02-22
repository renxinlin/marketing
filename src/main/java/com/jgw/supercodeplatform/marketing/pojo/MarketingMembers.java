package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingMembers {

    private int id;//序号
    private String wxId;//微信id号
    private String wxName;//微信id号
    private String mobile;//手机
    private String userId;//用户Id
    private String userName;//用户姓名
    private String sex;//性别
    private String birthday;//生日
    private String provinceCode;//省编码
    private String countyCode;//县编码
    private String cityCode;//市编码
    private String provinceName;//省名称
    private String countyName;//县名称
    private String cityName;//市名称
    private String registDate;//注册时间
    private String state;//状态(1、 表示正常，0 表示下线)
    private String organizationId;//组织Id
    private String organizationFullName;//组织全名
    private String newRegisterFlag;//是否新注册的标志(1  表示是，0 表示不是)
    private String createDate;//建立日期
    private String updateDate;//修改日期
    private String stores;//门店或经销商
    private String storesType;//门店或经销商类型（1表示门店 ，2表示经销商）
    private String babyBirthday;//宝宝生日


    public MarketingMembers() {
    }

    public MarketingMembers(int id, String wxId, String wxName, String mobile, String userId, String userName, String sex, String birthday, String provinceCode, String countyCode, String cityCode, String provinceName, String countyName, String cityName, String registDate, String state, String organizationId, String organizationFullName, String newRegisterFlag, String createDate, String updateDate, String stores, String storesType, String babyBirthday) {
        this.id = id;
        this.wxId = wxId;
        this.wxName = wxName;
        this.mobile = mobile;
        this.userId = userId;
        this.userName = userName;
        this.sex = sex;
        this.birthday = birthday;
        this.provinceCode = provinceCode;
        this.countyCode = countyCode;
        this.cityCode = cityCode;
        this.provinceName = provinceName;
        this.countyName = countyName;
        this.cityName = cityName;
        this.registDate = registDate;
        this.state = state;
        this.organizationId = organizationId;
        this.organizationFullName = organizationFullName;
        this.newRegisterFlag = newRegisterFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.stores = stores;
        this.storesType = storesType;
        this.babyBirthday = babyBirthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
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

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getStores() {
        return stores;
    }

    public void setStores(String stores) {
        this.stores = stores;
    }

    public String getStoresType() {
        return storesType;
    }

    public void setStoresType(String storesType) {
        this.storesType = storesType;
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
}
