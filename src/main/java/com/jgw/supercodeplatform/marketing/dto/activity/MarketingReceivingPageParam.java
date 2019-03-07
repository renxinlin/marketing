package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动领取页")
public class MarketingReceivingPageParam {

    @ApiModelProperty(value = "序列Id")
    private int id;//序号
    @ApiModelProperty(value = "模板Id")
    private String templateId;//模板Id
    @ApiModelProperty(value = "是否显示领取页面(1  表示显示，0 表示不显示)")
    private Byte isReceivePage;//是否显示领取页面(1  表示显示，0 表示不显示)
    @ApiModelProperty(value = "文本内容及字号，支持多文本")
    private String textContent;//文本内容及字号，支持多文本
    @ApiModelProperty(value = "图片地址")
    private String picAddress;//图片地址
    @ApiModelProperty(value = "公众号二维码是否显示(1  表示显示，0 表示不显示)")
    private Byte isQrcodeView;//公众号二维码是否显示(1  表示显示，0 表示不显示)
    @ApiModelProperty(value = "二维码Url")
    private String qrcodeUrl;//二维码Url
 
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

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }
}
