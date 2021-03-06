package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("非自卖产品:新增")
public class ProductUnsaleAddUpdateParam {
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

    /** sku数量 */
    @ApiModelProperty(value = "sku数量")
    private Integer unsaleProductSkuNum;


    /** 展示价 */
    @ApiModelProperty(value = "展示价",hidden = true)
    private Float showPrice;

    /** 售价 */
    @ApiModelProperty(value = "售价")
    private Float realPrice;

    /**  */
    @ApiModelProperty(value = "更新人id")
    private String updateUserId;

    /**  */
    @ApiModelProperty(value = "更新人")
    private String updateUserName;

    /**  */
    @ApiModelProperty(value = "更新日期")
    private Date updateDate;

    /**  */
    @ApiModelProperty(value = "创建人id")
    private String createUserId;

    /**  */
    @ApiModelProperty(value = "创建人")
    private String createUserName;

    /**  */
    @ApiModelProperty(value = "创建日期")
    private Date createDate;

    /**  */
    @ApiModelProperty(value = "组织id")
    private String organizationId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    /** 详情 */
    @ApiModelProperty(value = "详情")
    private String detail;

    // dto属性
    @ApiModelProperty(value = "sku")
    private List<SkuInfo> skuChild;


    @ApiModelProperty(value = "展示价前端交互")
    private String showPriceStr;


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

    public Integer getUnsaleProductSkuNum() {
        return unsaleProductSkuNum;
    }

    public void setUnsaleProductSkuNum(Integer unsaleProductSkuNum) {
        this.unsaleProductSkuNum = unsaleProductSkuNum;
    }

    public Float getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Float showPrice) {
        this.showPrice = showPrice;
    }

    public Float getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Float realPrice) {
        this.realPrice = realPrice;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<SkuInfo> getSkuChild() {
        return skuChild;
    }

    public void setSkuChild(List<SkuInfo> skuChild) {
        this.skuChild = skuChild;
    }

    public String getShowPriceStr() {
        return showPriceStr;
    }

    public void setShowPriceStr(String showPriceStr) {
        this.showPriceStr = showPriceStr;
    }
}
