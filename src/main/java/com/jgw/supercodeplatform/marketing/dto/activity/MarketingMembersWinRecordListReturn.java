package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "中奖记录返回类")
public class MarketingMembersWinRecordListReturn {

    @ApiModelProperty(value = "Id")
    private int id;//序列Id
    @ApiModelProperty(value = "活动id")
    private Long activityId;//活动id
    @ApiModelProperty(value = "活动设置id")
    private Long activitySetId;//活动设置id
    @ApiModelProperty(value = "奖品类型名称")
    private String activityName;//奖品类型名称
    @ApiModelProperty(value = "会员微信Id")
    private String openId;//会员微信Id
    @ApiModelProperty(value = "中奖奖次id")
    private Long prizeTypeId;//中奖奖次id
    @ApiModelProperty(value = "中奖金额")
    private String winningAmount;//中奖金额，入库float,出库String从而保留.00
    @ApiModelProperty(value = "中奖码")
    private String winningCode;//中奖码
    @ApiModelProperty(value = "组织id")
    private String organizationId;//组织id
    @ApiModelProperty(value = "会员手机号")
    private String mobile;//会员手机号
    @ApiModelProperty(value = "微信id号")
    private String wxName;//微信id号
    @ApiModelProperty(value = "用户姓名")
    private String userName;//用户姓名
    @ApiModelProperty(value = "活动产品名称")
    private String productName;//活动产品名称
    @ApiModelProperty(value = "奖品类型名称")
    private String prizeTypeName;//奖品类型名称
    @ApiModelProperty(value = "门店名称")
    private String customerName;//门店名称

    public MarketingMembersWinRecordListReturn() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivitySetId() {
        return activitySetId;
    }

    public void setActivitySetId(Long activitySetId) {
        this.activitySetId = activitySetId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getPrizeTypeId() {
        return prizeTypeId;
    }

    public void setPrizeTypeId(Long prizeTypeId) {
        this.prizeTypeId = prizeTypeId;
    }

    public String getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(String winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getWinningCode() {
        return winningCode;
    }

    public void setWinningCode(String winningCode) {
        this.winningCode = winningCode;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrizeTypeName() {
        return prizeTypeName;
    }

    public void setPrizeTypeName(String prizeTypeName) {
        this.prizeTypeName = prizeTypeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
