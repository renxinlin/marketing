package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 中奖页实体
 * @author czm
 *
 */
@ApiModel(value = "活动中奖页")
public class MarketingWinningPageParam {
	@ApiModelProperty(value = "序列Id")
    private int id;//序号
	@ApiModelProperty(value = "登录类型 1手机 2微信")
    private Byte loginType;//登录类型 1手机 2微信
	@ApiModelProperty(value = "中奖页模板id")
    private String templateId;//中奖页模板id
    
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
    
}
