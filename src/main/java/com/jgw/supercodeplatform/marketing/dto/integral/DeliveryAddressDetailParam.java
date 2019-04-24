package com.jgw.supercodeplatform.marketing.dto.integral;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 */
@ApiModel(value = "收货地址")
public class DeliveryAddressDetailParam {
    /** 主键 */
    @ApiModelProperty(value = "ID")
    private Long id;

    /** 会员id */
    @ApiModelProperty(value = "会员id")
    private Long memberId;


    /** 收货名称 */
    @ApiModelProperty(value = "收货人")
    private String name;

    /** 收货手机 */
    @ApiModelProperty(value = "收货手机")
    private String mobile;


    /** 详细地址 */
    @ApiModelProperty(value = "详细地址")
    private String detail;



    @ApiModelProperty(value = "是否默认true是，false不是")
    /** 是否默认0是，1不是 */
    private Boolean defaultUsingWeb;

    public Boolean getDefaultUsingWeb() {
        return defaultUsingWeb;
    }

    public void setDefaultUsingWeb(Boolean defaultUsingWeb) {
        this.defaultUsingWeb = defaultUsingWeb;
    }

    @ApiModelProperty(value = "前端省市区字段")
    private String pcccode;



    public String getPcccode() {
        return pcccode;
    }

    public void setPcccode(String pcccode) {
        this.pcccode = pcccode;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}