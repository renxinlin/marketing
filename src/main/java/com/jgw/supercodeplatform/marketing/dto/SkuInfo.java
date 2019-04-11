package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "非自卖产品SKU")
public class SkuInfo {
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "SKU图片")
    private String skuUrl;
    @ApiModelProperty(value = "SKU库存")
    private String haveStock;

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
