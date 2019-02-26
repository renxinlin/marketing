package com.jgw.supercodeplatform.marketing.pojo;
/**
 * 中奖页实体
 * @author czm
 *
 */
public class MarketingWinningPage {

    private int id;//序号
    private Byte loginType;//登录类型 1手机 2微信
    private String templateId;//中奖页模板id
    private Long activitySetId; //活动设置id
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Byte getLoginType() {
		return loginType;
	}
	public void setLoginType(Byte loginType) {
		this.loginType = loginType;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Long getActivitySetId() {
		return activitySetId;
	}
	public void setActivitySetId(Long activitySetId) {
		this.activitySetId = activitySetId;
	}
    
}
