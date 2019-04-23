package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MarketingReceivingPageMapper {
    static String allFields="Id id,TemplateId templateId,ActivitySetId activitySetId,IsReceivePage isReceivePage,TextContent textContent,PicAddress picAddress,IsQrcodeView isQrcodeView,QrcodeUrl qrcodeUrl,CreateDate createDate,UpdateDate updateDate";


    static String allFieldsWhenJoin="a.Id id,a.TemplateId templateId,a.ActivitySetId activitySetId,a.IsReceivePage isReceivePage,a.TextContent textContent,a.PicAddress picAddress,a.IsQrcodeView isQrcodeView,a.QrcodeUrl qrcodeUrl,a.CreateDate createDate,a.UpdateDate updateDate";

    @Select("select " + allFieldsWhenJoin + " ,b.OrganizationId organizationId ,b.OrganizatioIdlName organizatioIdlName  from marketing_template a left join marketing_activity_set b on a.ActivitySetId = b.Id where ActivitySetId=#{activitySetId}")
    MarketingReceivingPage getByActivityId(@Param("activitySetId")Long activitySetId);

    @Insert(" INSERT INTO marketing_template(TemplateId,ActivitySetId,IsReceivePage,TextContent,PicAddress,"
            + " IsQrcodeView,QrcodeUrl )"
            + " VALUES(#{templateId},#{activitySetId},#{isReceivePage},#{textContent},#{picAddress},"
            + " #{isQrcodeView},#{qrcodeUrl} )")
	int insert(MarketingReceivingPage mPage);

    @Update(" <script>"
            + " UPDATE marketing_template "
            + " <set>"
            + " <if test='templateId !=null and templateId != &apos;&apos; '> TemplateId = #{templateId} ,</if> "
            + " <if test='activitySetId !=null and activitySetId != &apos;&apos; '> ActivitySetId = #{activitySetId} ,</if> "
            + " <if test='isReceivePage !=null   '> IsReceivePage = #{isReceivePage} ,</if> "
            + " <if test='textContent !=null and textContent != &apos;&apos; '> TextContent = #{textContent} ,</if> "
            + " <if test='picAddress !=null and picAddress != &apos;&apos; '> PicAddress = #{picAddress} ,</if> "
            + " <if test='isQrcodeView !=null  '> IsQrcodeView = #{isQrcodeView} ,</if> "
            + " <if test='qrcodeUrl !=null and qrcodeUrl != &apos;&apos; '> QrcodeUrl = #{qrcodeUrl} ,</if> "
            + " <if test='updateDate !=null and updateDate != &apos;&apos; '> UpdateDate = NOW() ,</if> "
            + " </set>"
            + " <where> "
            + "  Id = #{id}"
            + " </where>"
            + " </script>")
	void update(MarketingReceivingPage mReceivingPage);

}
