package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("H5会员详情页")
public class IntegralExchangeDetailFirstParam {
    @ApiModelProperty("商品ID")
    private String productId;
    @ApiModelProperty("商品名称")
    private String  ProductName;
    @ApiModelProperty("商品图片")
    private String ProductPic;
    @ApiModelProperty("商品价格")
    private Integer exchangeIntegral;
    @ApiModelProperty("商品展示价")
    private String showPriceStr;
    @ApiModelProperty("兑换资源0非自卖1自卖产品")
    private Byte exchangeResource;
    @ApiModelProperty("支付手段：0积分")
    private Byte payWay;
    @ApiModelProperty("0无sku,1有sku")
    private Byte skuStatus;
    @ApiModelProperty("商品详情")
    private String detail;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPic() {
        return ProductPic;
    }

    public void setProductPic(String productPic) {
        ProductPic = productPic;
    }

    public Integer getExchangeIntegral() {
        return exchangeIntegral;
    }

    public void setExchangeIntegral(Integer exchangeIntegral) {
        this.exchangeIntegral = exchangeIntegral;
    }

    public String getShowPriceStr() {
        return showPriceStr;
    }

    public void setShowPriceStr(String showPriceStr) {
        this.showPriceStr = showPriceStr;
    }

    public Byte getExchangeResource() {
        return exchangeResource;
    }

    public void setExchangeResource(Byte exchangeResource) {
        this.exchangeResource = exchangeResource;
    }

    public Byte getPayWay() {
        return payWay;
    }

    public void setPayWay(Byte payWay) {
        this.payWay = payWay;
    }

    public Byte getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(Byte skuStatus) {
        this.skuStatus = skuStatus;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
