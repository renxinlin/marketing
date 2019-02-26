package com.jgw.supercodeplatform.marketing.dto.activity;

public class MarketingReceivingPageParam {

    private int id;//序号
    private String templateId;//模板Id
    private Byte isReceivePage;//是否显示领取页面(1  表示显示，0 表示不显示)
    private String textContent;//文本内容及字号，支持多文本
    private String picAddress;//图片地址
    private Byte isQrcodeView;//公众号二维码是否显示(1  表示显示，0 表示不显示)
 
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
}
