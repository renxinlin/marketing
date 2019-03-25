package com.jgw.supercodeplatform.marketing.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 领取页
 * @author czm
 *
 */
@ApiModel(value = "领取页")
public class MarketingReceivingPage {

    @ApiModelProperty(value = "序列Id")
    private int id;//序号
    @ApiModelProperty(value = "模板Id")
    private String templateId;//模板Id
    @ApiModelProperty(value = "活动设置主键id")
    private Long activitySetId;//活动设置主键id
    @ApiModelProperty(value = "是否显示领取页面(1  表示显示，0 表示不显示)")
    private Byte isReceivePage;//是否显示领取页面(1  表示显示，0 表示不显示)
    @ApiModelProperty(value = "文本内容及字号，支持多文本")
    private String textContent;//文本内容及字号，支持多文本
    @ApiModelProperty(value = "图片地址,支持多图片")
    private String picAddress;//图片地址,支持多图片
    @ApiModelProperty(value = "公众号二维码是否显示(1  表示显示，0 表示不显示)")
    private Byte isQrcodeView;//公众号二维码是否显示(1  表示显示，0 表示不显示)
    @ApiModelProperty(value = "二维码Url")
    private String qrcodeUrl;//二维码Url
    @ApiModelProperty(value = "建立时间")
    private String createDate;//建立时间
    @ApiModelProperty(value = "修改时间")
    private String updateDate;//修改时间

    @ApiModelProperty(value = "组织ID")
    private String organizationId;
    @ApiModelProperty(value = "组织名称")
    private String organizatioIdlName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Byte getIsReceivePage() {
		return isReceivePage;
	}

	public void setIsReceivePage(Byte isReceivePage) {
		this.isReceivePage = isReceivePage;
	}

	public Byte getIsQrcodeView() {
		return isQrcodeView;
	}

	public void setIsQrcodeView(Byte isQrcodeView) {
		this.isQrcodeView = isQrcodeView;
	}

	public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
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

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public Long getActivitySetId() {
        return activitySetId;
    }

    public void setActivitySetId(Long activitySetId) {
        this.activitySetId = activitySetId;
    }

    public void setOrganizatioIdlName(String organizatioIdlName) {
        this.organizatioIdlName = organizatioIdlName;
    }

    public String getOrganizatioIdlName() {
        return organizatioIdlName;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationId() {
        return organizationId;
    }
}
