package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("H5会员详情页")
public class IntegralExchangeDetailParam {
    @ApiModelProperty("商品ID")
    private String productId;
    @ApiModelProperty("商品名称")
    private String  productName;
    @ApiModelProperty("商品图片")
    private String productPic;
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
    @ApiModelProperty("库存量")
    private Integer haveStock;
    @ApiModelProperty("商品详情")
    private String detail;

    public Integer getHaveStock() {
        return haveStock;
    }

    public void setHaveStock(Integer haveStock) {
        this.haveStock = haveStock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
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
