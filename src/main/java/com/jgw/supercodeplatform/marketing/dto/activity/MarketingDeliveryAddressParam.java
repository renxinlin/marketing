package com.jgw.supercodeplatform.marketing.dto.activity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "中奖奖品添加收货")
public class MarketingDeliveryAddressParam {

    /** 会员id */
    @ApiModelProperty(value = "微信sate")
    private String wxstate;
    
    /** 奖品ID */
    private Long prizeId;

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
    
    /**  */
    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    /**  */
    @ApiModelProperty(value = "市编码")
    private String cityCode;

    /**  */
    @ApiModelProperty(value = "区编码")
    private String countryCode;

    /** 详细地址 */
    @ApiModelProperty(value = "详细地址")
    private String detail;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

}