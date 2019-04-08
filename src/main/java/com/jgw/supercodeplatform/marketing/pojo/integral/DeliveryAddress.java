package com.jgw.supercodeplatform.marketing.pojo.integral;

public class DeliveryAddress {
    /** 主键 */
    private Long id;

    /** 会员id */
    private Integer memberid;

    /** 会员名称 */
    private String membername;

    /** 收货名称 */
    private String name;

    /** 收货手机 */
    private String mobile;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String country;

    /** 街道 */
    private String street;

    /**  */
    private String provincecode;

    /**  */
    private String citycode;

    /**  */
    private String countrycode;

    /**  */
    private String streetcode;

    /** 详细地址 */
    private String detail;

    /** 邮编 */
    private String postcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getStreetcode() {
        return streetcode;
    }

    public void setStreetcode(String streetcode) {
        this.streetcode = streetcode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}