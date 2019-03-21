package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.*;

import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;

@Mapper
public interface MarketingWinningPageMapper {
    static String allFields="Id id,LoginType loginType,TemplateId templateId,ActivitySetId activitySetId";
    
    @Select("select "+allFields+" from marketing_winning_page where ActivitySetId=#{activitySetId}")
	MarketingWinningPage getByActivityId(@Param("activitySetId")Long activitySetId);


	@Insert(" INSERT INTO marketing_winning_page(LoginType,TemplateId,ActivitySetId )"
			+ " VALUES(#{loginType},#{templateId},#{activitySetId})")
	int insert(MarketingWinningPage mWinningPage);


	@Update(" <script>"
			+ " UPDATE marketing_winning_page "
			+ " <set>"
			+ " <if test='loginType !=null   '> LoginType = #{loginType} ,</if> "
			+ " <if test='templateId !=null and templateId != &apos;&apos; '> TemplateId = #{templateId} ,</if> "
			+ " <if test='activitySetId !=null and activitySetId != &apos;&apos; '> ActivitySetId = #{activitySetId} ,</if> "
			+ " </set>"
			+ " <where> "
			+ " Id = #{id} "
			+ " </where>"
			+ " </script>")
	void update(MarketingWinningPage mWinningPage);

}
