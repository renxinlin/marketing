package com.jgw.supercodeplatform.marketing.pojo;
/**
 * 领取页
 * @author czm
 *
 */
public class MarketingReceivingPage {
    private int id;//序号
    private String templateId;//模板Id
    private String isReceivePage;//是否显示领取页面(1  表示显示，0 表示不显示)
    private String textContent;//文本内容及字号，支持多文本
    private String fontSize1;//字号
    private String picAddress;//图片地址
    private String isQrcodeView;//公众号二维码是否显示(1  表示显示，0 表示不显示)
    private String qrcodeUrl;//二维码Url
    private String createDate;//建立时间
    private String updateDate;//修改时间

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

    public String getIsReceivePage() {
        return isReceivePage;
    }

    public void setIsReceivePage(String isReceivePage) {
        this.isReceivePage = isReceivePage;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getFontSize1() {
        return fontSize1;
    }

    public void setFontSize1(String fontSize1) {
        this.fontSize1 = fontSize1;
    }


    public String getIsQrcodeView() {
        return isQrcodeView;
    }

    public void setIsQrcodeView(String isQrcodeView) {
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
}
