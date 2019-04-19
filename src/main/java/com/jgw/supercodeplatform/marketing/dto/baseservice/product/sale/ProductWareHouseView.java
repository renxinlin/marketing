package com.jgw.supercodeplatform.marketing.dto.baseservice.product.sale;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel("产品仓储信息")
 public class ProductWareHouseView {
    @ApiModelProperty( "单个产品仓储唯一id")
    private String productWareHouseId="";
    @ApiModelProperty("产品id")
    private String productId="";
    @ApiModelProperty("包装层级 1,单品,2.两级.3.三级")
    private String packageLevel="";
    @ApiModelProperty("最小单位编码")
    private String smallUnitCode="";
    @ApiModelProperty("最小单位名称")
    private String smallUnitName="";
    @ApiModelProperty("成本价")
    private BigDecimal costPrice;
    @ApiModelProperty("进货价")
    private BigDecimal buyPrice;
    @ApiModelProperty( "进货价格单位编码")
    private String BuyPriceCode="";
    @ApiModelProperty( "进货价格单位名称")
    private String BuyPriceName="";
    @ApiModelProperty( "成本价格单位编码")
    private String priceCode="";
    @ApiModelProperty("成本价格单位名称")
    private String priceName="";
    @ApiModelProperty( "是否扫码入库 Y,是扫码入库 N,不是扫码入库")
    private String sweepCodeIn="";
    @ApiModelProperty("是否扫码出库 Y,是扫码出库 N,不是扫码出库")
    private String sweepCodeOut="";
    @ApiModelProperty( "库存最低预警值")
    private Integer lowerWarnValue;
    @ApiModelProperty(value = "最低预警值单位编码")
    private String warnUnitCode="";
    @ApiModelProperty("最低预警值单位名称")
    private String warnUnitName="";
    @ApiModelProperty("自定义信息")
    List<CustomInfoView>customInfoViews  = new ArrayList<>();
    @ApiModelProperty("包装规格/单位")
    List<ProductPackageRatioView> productPackageRatios = new ArrayList<>();

   public String getProductWareHouseId() {
      return productWareHouseId;
   }

   public void setProductWareHouseId(String productWareHouseId) {
      this.productWareHouseId = productWareHouseId;
   }

   public String getProductId() {
      return productId;
   }

   public void setProductId(String productId) {
      this.productId = productId;
   }

   public String getPackageLevel() {
      return packageLevel;
   }

   public void setPackageLevel(String packageLevel) {
      this.packageLevel = packageLevel;
   }

   public String getSmallUnitCode() {
      return smallUnitCode;
   }

   public void setSmallUnitCode(String smallUnitCode) {
      this.smallUnitCode = smallUnitCode;
   }

   public String getSmallUnitName() {
      return smallUnitName;
   }

   public void setSmallUnitName(String smallUnitName) {
      this.smallUnitName = smallUnitName;
   }

   public BigDecimal getCostPrice() {
      return costPrice;
   }

   public void setCostPrice(BigDecimal costPrice) {
      this.costPrice = costPrice;
   }

   public BigDecimal getBuyPrice() {
      return buyPrice;
   }

   public void setBuyPrice(BigDecimal buyPrice) {
      this.buyPrice = buyPrice;
   }

   public String getBuyPriceCode() {
      return BuyPriceCode;
   }

   public void setBuyPriceCode(String buyPriceCode) {
      BuyPriceCode = buyPriceCode;
   }

   public String getBuyPriceName() {
      return BuyPriceName;
   }

   public void setBuyPriceName(String buyPriceName) {
      BuyPriceName = buyPriceName;
   }

   public String getPriceCode() {
      return priceCode;
   }

   public void setPriceCode(String priceCode) {
      this.priceCode = priceCode;
   }

   public String getPriceName() {
      return priceName;
   }

   public void setPriceName(String priceName) {
      this.priceName = priceName;
   }

   public String getSweepCodeIn() {
      return sweepCodeIn;
   }

   public void setSweepCodeIn(String sweepCodeIn) {
      this.sweepCodeIn = sweepCodeIn;
   }

   public String getSweepCodeOut() {
      return sweepCodeOut;
   }

   public void setSweepCodeOut(String sweepCodeOut) {
      this.sweepCodeOut = sweepCodeOut;
   }

   public Integer getLowerWarnValue() {
      return lowerWarnValue;
   }

   public void setLowerWarnValue(Integer lowerWarnValue) {
      this.lowerWarnValue = lowerWarnValue;
   }

   public String getWarnUnitCode() {
      return warnUnitCode;
   }

   public void setWarnUnitCode(String warnUnitCode) {
      this.warnUnitCode = warnUnitCode;
   }

   public String getWarnUnitName() {
      return warnUnitName;
   }

   public void setWarnUnitName(String warnUnitName) {
      this.warnUnitName = warnUnitName;
   }

   public List<CustomInfoView> getCustomInfoViews() {
      return customInfoViews;
   }

   public void setCustomInfoViews(List<CustomInfoView> customInfoViews) {
      this.customInfoViews = customInfoViews;
   }

   public List<ProductPackageRatioView> getProductPackageRatios() {
      return productPackageRatios;
   }

   public void setProductPackageRatios(List<ProductPackageRatioView> productPackageRatios) {
      this.productPackageRatios = productPackageRatios;
   }
}
