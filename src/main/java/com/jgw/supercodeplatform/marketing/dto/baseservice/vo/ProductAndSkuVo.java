package com.jgw.supercodeplatform.marketing.dto.baseservice.vo;

import com.jgw.supercodeplatform.marketing.dto.integral.SkuInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 基础信心转VO,与前端交互
 */
@ApiModel("自卖非自卖产品VO")
public class ProductAndSkuVo {
    @ApiModelProperty("产品ID")
    private String productId;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品图片")
    private String productPic;
    @ApiModelProperty("产品售价字符串|为nullH5不展示")
    private String showPriceStr;
    @ApiModelProperty("产品SKU")
    private List<SkuInfo> skuInfo;


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

    public String getShowPriceStr() {
        return showPriceStr;
    }

    public void setShowPriceStr(String showPriceStr) {
        this.showPriceStr = showPriceStr;
    }

    public List<SkuInfo> getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(List<SkuInfo> skuInfo) {
        this.skuInfo = skuInfo;
    }
}
