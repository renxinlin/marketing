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
    private String pruductId;
    @ApiModelProperty("产品名称")
    private String pruductName;
    @ApiModelProperty("产品图片")
    private String pruductPic;
    @ApiModelProperty("产品售价字符串|为nullH5不展示")
    private String showPriceStr;
    @ApiModelProperty("产品SKU")
    private List<SkuInfo> skuInfo;


    public String getPruductId() {
        return pruductId;
    }

    public void setPruductId(String pruductId) {
        this.pruductId = pruductId;
    }

    public String getPruductName() {
        return pruductName;
    }

    public void setPruductName(String pruductName) {
        this.pruductName = pruductName;
    }

    public String getPruductPic() {
        return pruductPic;
    }

    public void setPruductPic(String pruductPic) {
        this.pruductPic = pruductPic;
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
