package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("核销信息")
public class MarketingMemberCoupon {
    
	private Long id;

    private Long memberId;

    private Long couponId;
    @ApiModelProperty("抵扣券码")
    private String couponCode;
    @ApiModelProperty("抵扣券金额")
    private Double couponAmount;
    @ApiModelProperty("会员手机号")
    private String memberPhone;

    private String productId;

    private String productBatchId;

    private String productBatchName;

    private String sbatchId;
    @ApiModelProperty("产品名称")
    private String productName;

    private String obtainCustomerId;
    @ApiModelProperty("获得渠道名称")
    private String obtainCustmerName;

    private Date deductionDate;
    @ApiModelProperty("获得时间")
    private Date createTime;

    private String verifyCustomerId;
    @ApiModelProperty("核销渠道名称")
    private String verifyCustomerName;

    private String verifyPersonName;

    private String verifyPersonPhone;

    private Date verifyTime;

    private Byte verifyPersonType;

    private Byte used;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductBatchId() {
        return productBatchId;
    }

    public void setProductBatchId(String productBatchId) {
        this.productBatchId = productBatchId;
    }

    public String getProductBatchName() {
        return productBatchName;
    }

    public void setProductBatchName(String productBatchName) {
        this.productBatchName = productBatchName;
    }

    public String getSbatchId() {
        return sbatchId;
    }

    public void setSbatchId(String sbatchId) {
        this.sbatchId = sbatchId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getObtainCustomerId() {
        return obtainCustomerId;
    }

    public void setObtainCustomerId(String obtainCustomerId) {
        this.obtainCustomerId = obtainCustomerId;
    }

    public Date getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(Date deductionDate) {
        this.deductionDate = deductionDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVerifyCustomerId() {
        return verifyCustomerId;
    }

    public void setVerifyCustomerId(String verifyCustomerId) {
        this.verifyCustomerId = verifyCustomerId;
    }

    public String getVerifyCustomerName() {
        return verifyCustomerName;
    }

    public void setVerifyCustomerName(String verifyCustomerName) {
        this.verifyCustomerName = verifyCustomerName;
    }

    public String getVerifyPersonName() {
        return verifyPersonName;
    }

    public void setVerifyPersonName(String verifyPersonName) {
        this.verifyPersonName = verifyPersonName;
    }

    public String getVerifyPersonPhone() {
        return verifyPersonPhone;
    }

    public void setVerifyPersonPhone(String verifyPersonPhone) {
        this.verifyPersonPhone = verifyPersonPhone;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Byte getVerifyPersonType() {
        return verifyPersonType;
    }

    public void setVerifyPersonType(Byte verifyPersonType) {
        this.verifyPersonType = verifyPersonType;
    }

    public Byte getUsed() {
        return used;
    }

    public void setUsed(Byte used) {
        this.used = used;
    }

	public Double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getObtainCustmerName() {
		return obtainCustmerName;
	}

	public void setObtainCustmerName(String obtainCustmerName) {
		this.obtainCustmerName = obtainCustmerName;
	}
}