package com.jgw.supercodeplatform.marketing.common.model.activity;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class ScanCodeInfoMO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String codeId;//外码,跳转到营销扫码接口时获取
	private String codeTypeId;//码值id,跳转到营销扫码接口时获取
	private String productId;//产品id,跳转到营销扫码接口时获取
	private String productBatchId;//码平台传的产品批次id,跳转到营销扫码接口时获取
    private String openId;//当前扫码用户openid授权接口获取
    private Long activitySetId;//当前扫码的码参与的活动设置id,跳转到营销扫码接口时获取
    private String organizationId;//当前扫码所属企业id,跳转到营销扫码接口时获取
    private String createTime;//将用于定时任务检查清除已经长时间未用的扫码缓存 yyyy-MM-dd HH:mm:ss
    private String mobile;//登录的手机号
	private Long userId;//用户id
	private String sbatchId;
	private String productName;

	// 目前没用到，防止后期产品多维度查询
	private Long activityId;//用户id
	private Byte activityType;//用户id
	private Date scanCodeTime;//扫码时间


	public Date getScanCodeTime() {
		return scanCodeTime;
	}

	public void setScanCodeTime(Date scanCodeTime) {
		this.scanCodeTime = scanCodeTime;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Byte getActivityType() {
		return activityType;
	}

	public void setActivityType(Byte activityType) {
		this.activityType = activityType;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeTypeId() {
		return codeTypeId;
	}

	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getActivitySetId() {
		return activitySetId;
	}

	public void setActivitySetId(Long activitySetId) {
		this.activitySetId = activitySetId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("codeId", codeId).append("codeTypeId", codeTypeId).append("productId", productId).append("sbatchId", sbatchId)
				.append("productBatchId", productBatchId).append("openId", openId).append("activitySetId", activitySetId).append("organizationId", organizationId).append("创建时间", createTime).toString();
	}

	public String getSbatchId() {
		return sbatchId;
	}

	public void setSbatchId(String sbatchId) {
		this.sbatchId = sbatchId;
	}

}
