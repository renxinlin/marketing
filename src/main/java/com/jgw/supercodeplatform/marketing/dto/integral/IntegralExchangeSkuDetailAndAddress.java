package com.jgw.supercodeplatform.marketing.dto.integral;

import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 聚合根:SKU信息和地址信息
 */
@ApiModel("积分sku和地址详情页")
public class IntegralExchangeSkuDetailAndAddress {
    @ApiModelProperty("产品id")
    private String productId;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品图片")
    private String productPic;
    @ApiModelProperty("sku信息")
    private List<SkuInfo> skuInfos;
    @ApiModelProperty("地址信息")
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
