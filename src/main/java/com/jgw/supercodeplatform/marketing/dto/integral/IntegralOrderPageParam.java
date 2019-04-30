package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel("积分订单分页VO")
public class IntegralOrderPageParam {
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 订单业务主键 */
    @ApiModelProperty(value = "订单业务主键")
    private String orderId;

    /** 兑换资源0非自卖1自卖产品 */
    @ApiModelProperty(value = "兑换资源0非自卖1自卖产品")
    private Byte exchangeResource;

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
    @ApiModelProperty(value = "物流状态0待发货1已发货")
    private Byte status;

    @ApiModelProperty(value = "excel使用物流状态0待发货1已发货",hidden = true)
    private String statusName;



    /** 会员id */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /** 会员名称 */
    @ApiModelProperty(value = "会员名称")
    private String memberName;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /** excel 日期格式携带时区信息问题*/
    @ApiModelProperty(value = "创建时间str",hidden = true)
    private String createDateStr;

    /** 发货时间 */
    @ApiModelProperty(value = "发货时间")
    private Date deliveryDate;


    /** 组织id */
    @ApiModelProperty(value = "组织id")
    private String organizationId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称")
    private String organizationName;



    /** 展示价 */
    @ApiModelProperty(value = "showPriceStr，展示价前端交互")
    private String showPriceStr;

    /** 图片 */
    @ApiModelProperty(value = "产品图片")
    private String productPic;



    /** 图片 */
    @ApiModelProperty(value = "skuid")
    private String skuId;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getShowPriceStr() {
        return showPriceStr;
    }

    public void setShowPriceStr(String showPriceStr) {
        this.showPriceStr = showPriceStr;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

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

    public Byte getExchangeResource() {
        return exchangeResource;
    }

    public void setExchangeResource(Byte exchangeResource) {
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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
}
