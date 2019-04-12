package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 *
 */
@ApiModel(value = "非自卖产品列表VO")
public class ProductUnsaleParam {
    /** 主键 */
    @ApiModelProperty(value = "不用于对接前端和其他接口")
    private Long id;

    /** 主键，与基础信息保持一致 */
    @ApiModelProperty(value = "主键，与基础信息保持一致,对接前端和其他接口 ")
    private String productId;

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String unsaleProductName;

    /** 图片 */
    @ApiModelProperty(value = "图片")
    private String unsaleProductPic;

    @ApiModelProperty(value = "sku")
    private List skuInfos;


    /**  */
    @ApiModelProperty(value = "更新人id")
    private String updateUserId;

    /**  */
    @ApiModelProperty(value = "更新人")
    private String updateUserName;

    /**  */
    @ApiModelProperty(value = "更新日期")
    private Date updateDate;


    public List getSkuInfos() {
        return skuInfos;
    }

    public void setSkuInfos(List skuInfos) {
        this.skuInfos = skuInfos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUnsaleProductName() {
        return unsaleProductName;
    }

    public void setUnsaleProductName(String unsaleProductName) {
        this.unsaleProductName = unsaleProductName;
    }

    public String getUnsaleProductPic() {
        return unsaleProductPic;
    }

    public void setUnsaleProductPic(String unsaleProductPic) {
        this.unsaleProductPic = unsaleProductPic;
    }


    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
