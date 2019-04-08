package com.jgw.supercodeplatform.marketing.pojo.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel(value = "订单")
public class IntegralOrder {
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 订单业务主键 */
    @ApiModelProperty(value = "订单业务主键")
    private String orderId;

    /** 兑换资源0非自卖1自卖产品 */
    @ApiModelProperty(value = "兑换资源0非自卖1自卖产品")
    private Boolean exchangeResource;

    /** 产品id|UUID */
    @ApiModelProperty(value = "产品id|UUID ")
    private String productId;

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /** sku信息 */
    @ApiModelProperty(value = "sku信息")
    private String skuName;

    /** sku图片url */
    @ApiModelProperty(value = "sku图片url")
    private String skuUrl;

    /** 兑换积分【单积分*数量】 */
    @ApiModelProperty(value = "兑换积分【单积分*数量】")
    private Integer exchangeIntegralNum;

    /** 兑换数量 */
    @ApiModelProperty(value = "兑换数量")
    private Integer exchangeNum;

    /** 收货名 */
    @ApiModelProperty(value = "收货人")
    private String name;

    /** 收货手机 */
    @ApiModelProperty(value = "收货手机")
    private String mobile;

    /** 收货地址 */
    @ApiModelProperty(value = "收货地址")
    private String address;

    /** 物流状态0待发货1已发货 */
    @ApiModelProperty(value = "主键")
    private String status;

    /** 会员id */
    @ApiModelProperty(value = "会员id")
    private Integer memberId;

    /** 会员名称 */
    @ApiModelProperty(value = "会员名称")
    private String memberName;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /** 更新人ID */
    @ApiModelProperty(value = "更新人ID")
    private Integer updateUserId;

    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String updateUserName;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    /** 组织id */
    @ApiModelProperty(value = "组织id")
    private String organizationId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getExchangeResource() {
        return exchangeResource;
    }

    public void setExchangeResource(Boolean exchangeResource) {
        this.exchangeResource = exchangeResource;
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public Integer getExchangeIntegralNum() {
        return exchangeIntegralNum;
    }

    public void setExchangeIntegralNum(Integer exchangeIntegralNum) {
        this.exchangeIntegralNum = exchangeIntegralNum;
    }

    public Integer getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(Integer exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}