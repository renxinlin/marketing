package com.jgw.supercodeplatform.marketing.dto.integral;

import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;

import java.util.List;

/**
 * 聚合根:SKU信息和地址信息
 */
public class IntegralExchangeSkuDetailAndAddress {

    private String productId;
    private String productName;
    private String productPic;
    private List<SkuInfo> skuInfos;
    private DeliveryAddress deliveryAddress;

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
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

    public List<SkuInfo> getSkuInfos() {
        return skuInfos;
    }

    public void setSkuInfos(List<SkuInfo> skuInfos) {
        this.skuInfos = skuInfos;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
