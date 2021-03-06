package com.jgw.supercodeplatform.marketing.common.model.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;

import java.util.List;

public class MarketingActivityListMO {
	private Long id;
	private Long activityId;// 活动主键Id
	private String organizationId;// 组织Id
	private String organizatioIdlName;// 组织
	private String activityTitle;// 活动标题
	private String activityStartDate;// 活动开始时间
	private String activityEndDate;// 活动结束时间
	private String updateUserId;// 更新用户Id
	private String updateUserName;// 更新用户名称
	private String createDate;// 创建时间
	private String updateDate;// 更新时间
	private Integer activityStatus;// 活动状态(1、表示上架进展，0 表示下架)
	private Integer eachDayNumber;// 每人每天次数
	private Integer activityRangeMark;// 活动范围标志(1、表示部分产品有效 2、表示全部产品有效 )
	private Integer autoFetch;// 是否自动获取(1、自动获取 2、仅此一次 )
	private Long codeTotalNum;// 参与该活动一共的码数
	private Long id1;// 大转盘签到活动

	private String activityName;
	private List<MarketingChannel> marketingChannels;
	private List<MarketingActivityProduct> maActivityProducts;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizatioIdlName() {
		return organizatioIdlName;
	}
	public void setOrganizatioIdlName(String organizatioIdlName) {
		this.organizatioIdlName = organizatioIdlName;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public String getActivityStartDate() {
		return activityStartDate;
	}
	public void setActivityStartDate(String activityStartDate) {
		this.activityStartDate = activityStartDate;
	}
	public String getActivityEndDate() {
		return activityEndDate;
	}
	public void setActivityEndDate(String activityEndDate) {
		this.activityEndDate = activityEndDate;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public Integer getEachDayNumber() {
		return eachDayNumber;
	}
	public void setEachDayNumber(Integer eachDayNumber) {
		this.eachDayNumber = eachDayNumber;
	}
	public Integer getActivityRangeMark() {
		return activityRangeMark;
	}
	public void setActivityRangeMark(Integer activityRangeMark) {
		this.activityRangeMark = activityRangeMark;
	}
	public Integer getAutoFetch() {
		return autoFetch;
	}
	public void setAutoFetch(Integer autoFetch) {
		this.autoFetch = autoFetch;
	}
	public Long getCodeTotalNum() {
		return codeTotalNum;
	}
	public void setCodeTotalNum(Long codeTotalNum) {
		this.codeTotalNum = codeTotalNum;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public List<MarketingChannel> getMarketingChannels() {
		return marketingChannels;
	}
	public void setMarketingChannels(List<MarketingChannel> marketingChannels) {
		this.marketingChannels = marketingChannels;
	}
	public List<MarketingActivityProduct> getMaActivityProducts() {
		return maActivityProducts;
	}
	public void setMaActivityProducts(List<MarketingActivityProduct> maActivityProducts) {
		this.maActivityProducts = maActivityProducts;
	}


	public Long getId1() {
		return id1;
	}

	public void setId1(Long id1) {
		this.id1 = id1;
	}
}
