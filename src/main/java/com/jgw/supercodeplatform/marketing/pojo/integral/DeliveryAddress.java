package com.jgw.supercodeplatform.marketing.pojo.integral;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "收货地址")
public class DeliveryAddress {
    /** 主键 */
    @ApiModelProperty(value = "ID")
    private Long id;

    /** 会员id */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /** 会员名称 */
    @ApiModelProperty(value = "会员名称")
    private String memberName;

    /** 收货名称 */
    @ApiModelProperty(value = "收货人")
    private String name;

    /** 收货手机 */
    @ApiModelProperty(value = "收货手机")
    private String mobile;

    /** 省 */
    @ApiModelProperty(value = "省")
    private String province;

    /** 市 */
    @ApiModelProperty(value = "市")
    private String city;

    /** 区 */
    @ApiModelProperty(value = "区")
    private String country;

    /** 街道 */
    @ApiModelProperty(value = "街道")
    private String street;

    /**  */
    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    /**  */
    @ApiModelProperty(value = "市编码")
    private String cityCode;

    /**  */
    @ApiModelProperty(value = "区编码")
    private String countryCode;

    /**  */
    @ApiModelProperty(value = "街道编码")
    private String streetCode;

    /** 详细地址 */
    @ApiModelProperty(value = "详细地址")
    private String detail;

    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    private String postcode;


    @ApiModelProperty(value = "是否默认0是，1不是")
    /** 是否默认0是，1不是 */
    private Byte defaultUsing;



    @ApiModelProperty(value = "前端省市区字段")
    private String pcccode;


    @ApiModelProperty(value = "前端详情地址信息")
    private String detailAll;


    public String getDetailAll() {
        return detailAll;
    }

    public void setDetailAll(String detailAll) {
        this.detailAll = detailAll;
    }

    public String getPcccode() {
        return pcccode;
    }

    public void setPcccode(String pcccode) {
        this.pcccode = pcccode;
    }

    public Byte getDefaultUsing() {
        return defaultUsing;
    }

    public void setDefaultUsing(Byte defaultUsing) {
        this.defaultUsing = defaultUsing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
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