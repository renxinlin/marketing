package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;

@Mapper
public interface MarketingReceivingPageMapper {
    static String allFields="Id id,TemplateId templateId,ActivitySetId activitySetId,IsReceivePage isReceivePage,TextContent textContent,PicAddress picAddress,IsQrcodeView isQrcodeView,QrcodeUrl qrcodeUrl,CreateDate createDate,UpdateDate updateDate";
	
    @Select("select "+allFields+" from marketing_ template where ActivitySetId=#{activitySetId}")
    MarketingReceivingPage getByActivityId(@Param("activitySetId")Long activitySetId);

	int insert(MarketingReceivingPage mPage);

	void update(MarketingReceivingPage mReceivingPage);

}
