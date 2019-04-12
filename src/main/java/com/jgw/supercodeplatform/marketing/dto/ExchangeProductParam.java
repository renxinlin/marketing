package com.jgw.supercodeplatform.marketing.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("积分兑换：操作")
public class ExchangeProductParam {
    // 基本校验，地址，兑换数量，memberID,productID,组织id,sku信息【可有可无】
    // 收货地址 收货手机 收货名
    @ApiModelProperty("会员ID")
    private Long memberId;
    @ApiModelProperty("商品id")
    private String productId;
    @ApiModelProperty("组织id")
    private String organizationId;
    @ApiModelProperty("收获名")
    private String name;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("sku图片")
    private String skuUrl;
    @ApiModelProperty("兑换数量")
    private Integer exchangeNum;
    @ApiModelProperty("地址")
    private String address;




    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public Integer getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(Integer exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
