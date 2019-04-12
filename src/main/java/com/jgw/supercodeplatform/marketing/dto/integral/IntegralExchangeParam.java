package com.jgw.supercodeplatform.marketing.dto.integral;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "H5兑换首页兑换商品")
public class IntegralExchangeParam {
    /** 产品id【业务主键，兼容基础平台】 */
    @ApiModelProperty(value = "产品id【业务主键，兼容基础平台】")
    private String productId;

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName;
    /** 产品图片 */
    @ApiModelProperty(value = "产品图片")
    private String productPic;

    /** 兑换积分 */
    @ApiModelProperty(value = "每个产品兑换积分")
    private Integer exchangeIntegral;

    /** 兑换积分 */
    @ApiModelProperty(value = "展示价")
    private String showPriceStr;

    public String getShowPriceStr() {
        return showPriceStr;
    }

    public void setShowPriceStr(String showPriceStr) {
        this.showPriceStr = showPriceStr;
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
}
