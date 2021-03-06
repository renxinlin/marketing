package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 新增兑换的产品数据
 */
@ApiModel("兑换产品信息")
public class ProductAddParam {
    @ApiModelProperty("产品id")
    private String productId;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品图片")
    private String productPic;
    @ApiModelProperty("产品展示价")
    private String showPriceStr;
    @ApiModelProperty("sku信息|只需要传入sku名称即可")
    private List<SkuInfo> skuInfo;

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getShowPriceStr() {
        return showPriceStr;
    }

    public void setShowPriceStr(String showPriceStr) {
        this.showPriceStr = showPriceStr;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<SkuInfo> getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(List<SkuInfo> skuInfo) {
        this.skuInfo = skuInfo;
    }
}
