package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "非自卖产品SKU")
public class SkuInfo {
    @ApiModelProperty(value = "skuid")
    private String skuId;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "SKU图片")
    private String skuUrl;
    @ApiModelProperty(value = "SKU库存|新增兑换时候不用传递")
    private String haveStock;

    /** 兑换积分 */
    @ApiModelProperty(value = "每个产品兑换积分")
    private Integer exchangeIntegral;

    public Integer getExchangeIntegral() {
        return exchangeIntegral;
    }

    public void setExchangeIntegral(Integer exchangeIntegral) {
        this.exchangeIntegral = exchangeIntegral;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getHaveStock() {
        return haveStock;
    }

    public void setHaveStock(String haveStock) {
        this.haveStock = haveStock;
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
}
