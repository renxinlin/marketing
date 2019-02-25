package com.jgw.supercodeplatform.marketing.pojo;

/**
 * 活动实体
 * @author czm
 *
 */
public class MarketingActivity {
	private Long id;

	private Byte activityType;// 活动类型1微信红包

	private String activityName;// 活动名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getActivityType() {
		return activityType;
	}

	public void setActivityType(Byte activityType) {
		this.activityType = activityType;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

}
