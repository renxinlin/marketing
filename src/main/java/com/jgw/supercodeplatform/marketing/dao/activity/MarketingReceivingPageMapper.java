package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.*;

import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;

@Mapper
public interface MarketingReceivingPageMapper {
    static String allFields="Id id,TemplateId templateId,ActivitySetId activitySetId,IsReceivePage isReceivePage,TextContent textContent,PicAddress picAddress,IsQrcodeView isQrcodeView,QrcodeUrl qrcodeUrl,CreateDate createDate,UpdateDate updateDate";
	
    @Select("select "+allFields+" from marketing_ template where ActivitySetId=#{activitySetId}")
    MarketingReceivingPage getByActivityId(@Param("activitySetId")Long activitySetId);

    @Insert(" INSERT INTO marketing_ template(TemplateId,ActivitySetId,IsReceivePage,TextContent,PicAddress,"
            + " IsQrcodeView,QrcodeUrl )"
            + " VALUES(#{templateId},#{activitySetId},#{isReceivePage},#{textContent},#{picAddress},"
            + " #{isQrcodeView},#{qrcodeUrl} )")
	int insert(MarketingReceivingPage mPage);

    @Update(" <script>"
            + " UPDATE marketing_ template "
            + " <set>"
            + " <if test='templateId !=null and templateId != &apos;&apos; '> TemplateId = #{templateId} ,</if> "
            + " <if test='activitySetId !=null and activitySetId != &apos;&apos; '> ActivitySetId = #{activitySetId} ,</if> "
            + " <if test='isReceivePage !=null and isReceivePage != &apos;&apos; '> IsReceivePage = #{isReceivePage} ,</if> "
            + " <if test='provinceCode !=null and provinceCode != &apos;&apos; '> ProvinceCode = #{provinceCode} ,</if> "
            + " <if test='textContent !=null and textContent != &apos;&apos; '> TextContent = #{textContent} ,</if> "
            + " <if test='picAddress !=null and picAddress != &apos;&apos; '> PicAddress = #{picAddress} ,</if> "
            + " <if test='isQrcodeView !=null and isQrcodeView != &apos;&apos; '> IsQrcodeView = #{isQrcodeView} ,</if> "
            + " <if test='qrcodeUrl !=null and qrcodeUrl != &apos;&apos; '> QrcodeUrl = #{qrcodeUrl} ,</if> "
            + " <if test='updateDate !=null and updateDate != &apos;&apos; '> UpdateDate = NOW() ,</if> "
            + " </set>"
            + " <where> "
            + " <if test='id !=null and id != &apos;&apos; '> and Id = #{id} </if>"
            + " </where>"
            + " </script>")
	void update(MarketingReceivingPage mReceivingPage);

}
