package com.jgw.supercodeplatform.marketing.dto.baseservice.product.sale;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("")
public class ProductPackageRatioView {
    @ApiModelProperty(value = "产品信息包装规格唯一编号")
    private String productPackageRatioId;
    @ApiModelProperty(value = "上级数量")
    private Integer firstNumber;
    @ApiModelProperty(value = "上级数量单位编码")
    private String firstNumberCode;
    @ApiModelProperty(value = "上级数量单位名称")
    private String firstNumberName;
    @ApiModelProperty( value = "下级数量")
    private Integer lastNumber;
    @ApiModelProperty( value = "下级数量单位编码")
    private String lastNumberCode;
    @ApiModelProperty( value = "下级数量单位名称")
    private String lastNumberName;
    @ApiModelProperty( value = "上级产品比例Id")
    private String farentId;


    public String getProductPackageRatioId() {
        return productPackageRatioId;
    }

    public void setProductPackageRatioId(String productPackageRatioId) {
        this.productPackageRatioId = productPackageRatioId;
    }

    public Integer getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(Integer firstNumber) {
        this.firstNumber = firstNumber;
    }

    public String getFirstNumberCode() {
        return firstNumberCode;
    }

    public void setFirstNumberCode(String firstNumberCode) {
        this.firstNumberCode = firstNumberCode;
    }

    public String getFirstNumberName() {
        return firstNumberName;
    }

    public void setFirstNumberName(String firstNumberName) {
        this.firstNumberName = firstNumberName;
    }

    public Integer getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Integer lastNumber) {
        this.lastNumber = lastNumber;
    }

    public String getLastNumberCode() {
        return lastNumberCode;
    }

    public void setLastNumberCode(String lastNumberCode) {
        this.lastNumberCode = lastNumberCode;
    }

    public String getLastNumberName() {
        return lastNumberName;
    }

    public void setLastNumberName(String lastNumberName) {
        this.lastNumberName = lastNumberName;
    }

    public String getFarentId() {
        return farentId;
    }

    public void setFarentId(String farentId) {
        this.farentId = farentId;
    }
}
