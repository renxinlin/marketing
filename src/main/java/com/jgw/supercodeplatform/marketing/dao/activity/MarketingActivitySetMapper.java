package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MarketingActivitySetMapper {
 static String allFields="Id id,ActivityId ActivityId,ActivityTitle ActivityTitle,ActivityStartDate ActivityStartDate,"
 		+ "ActivityEndDate ActivityEndDate,UpdateUserName UpdateUserName,UpdateUserId UpdateUserId,CreateDate createDate,UpdateDate UpdateDate,"
 		+ "ActivityStatus ActivityStatus,EachDayNumber EachDayNumber,ActivityRangeMark ActivityRangeMark,"
 		+ "autoFetch autoFetch,CodeTotalNum codeTotalNum,OrganizationId organizationId,OrganizatioIdlName organizatioIdlName,"
 		+ "ActivityDesc activityDesc,ConsumeIntegralNum consumeIntegralNum";



    @Select("select "+allFields+" from marketing_activity_set where Id=#{activitySetId}")
    MarketingActivitySet selectById(Long activitySetId);
 
    @Select("select EachDayNumber from marketing_activity_set where Id=#{activitySetId}")
    Integer selectEachDayNumber(Long activitySetId);


    @Update(" <script>"
            + " UPDATE marketing_activity_set "
            + " <set>"
            + "  ActivityStatus = #{activityStatus} "
            + " </set>"
            + " <where> "
            + "  Id = #{activitySetId} "
            + " </where>"
            + " </script>")
    int updateActivitySetStatus(MarketingActivitySetStatusUpdateParam mUpdateStatus);


   @Insert(" INSERT INTO marketing_activity_set(ActivityId,OrganizationId,OrganizatioIdlName,ActivityTitle,"
           + " ActivityStartDate,ActivityEndDate,UpdateUserId,UpdateUserName,ActivityStatus,EachDayNumber,"
           + " ActivityRangeMark,autoFetch,CodeTotalNum,CreateDate,UpdateDate,ConsumeIntegralNum,ActivityDesc) "
           + " VALUES(#{ma.activityId},#{ma.organizationId},#{ma.organizatioIdlName},#{ma.activityTitle},#{ma.activityStartDate},"
           + "#{ma.activityEndDate},#{ma.updateUserId},#{ma.updateUserName},#{ma.activityStatus},#{ma.eachDayNumber},#{ma.activityRangeMark}, "
           + "#{ma.autoFetch},#{ma.codeTotalNum},NOW(),NOW(),#{ma.consumeIntegralNum} ,#{ma.activityDesc}"
           + ")")
   @Options(useGeneratedKeys=true, keyProperty="ma.id", keyColumn="Id")
   int insert(@Param("ma")MarketingActivitySet marketingActivitySet);

   @Update(" <script>"
           + " UPDATE marketing_activity_set "
           + " <set>"
           + "  CodeTotalNum = #{codeTotalNum} "
           + " </set>"
           + " <where> "
           + " Id = #{id} "
           + " </where>"
           + " </script>")
   void updateCodeTotalNum(@Param("id")Long id,@Param("codeTotalNum") Long codeSum);
   
   
   @Select("select "+allFields+" from marketing_activity_set where ActivityTitle=#{activityTitle} and OrganizationId=#{organizationId}")
   MarketingActivitySet selectByTitleOrgId(@Param("activityTitle")String activityTitle, @Param("organizationId")String organizationId);
  
   @Update("update marketing_activity_set set CodeTotalNum =CodeTotalNum+#{codeTotalNum} where Id = #{id}")
   void addCodeTotalNum(@Param("codeTotalNum")Long codeNum, @Param("id")Long activitySetid);

    @Select("select "+allFields+" from marketing_activity_set where ActivityTitle=#{activityTitle} and OrganizationId=#{organizationId} and Id != #{id}")
    MarketingActivitySet selectByTitleOrgIdWithOutSelf(@Param("activityTitle")String activityTitle, @Param("organizationId")String organizationId ,@Param("id")Long activitySetid);


    @Update(" update marketing_activity_set " +
            "<set> "
            + " <if test='activityId !=null and activityId != &apos;&apos; '> ActivityId = #{activityId} ,</if> "
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '> OrganizationId = #{organizationId} ,</if> "
            + " <if test='organizatioIdlName !=null and organizatioIdlName != &apos;&apos; '> OrganizatioIdlName = #{organizatioIdlName} ,</if> "
            + " <if test='activityTitle !=null and activityTitle != &apos;&apos; '> ActivityTitle = #{activityTitle} ,</if> "
            + " <if test='activityStartDate !=null and activityStartDate != &apos;&apos; '> ActivityStartDate = #{activityStartDate} ,</if> "
            + " <if test='activityEndDate !=null and activityEndDate != &apos;&apos; '> ActivityEndDate = #{activityEndDate} ,</if> "
            + " <if test='updateUserId !=null and updateUserId != &apos;&apos; '> UpdateUserId = #{updateUserId} ,</if> "
            + " <if test='updateUserName !=null and updateUserName != &apos;&apos; '> UpdateUserName = #{updateUserName} ,</if> "
            + " <if test='updateDate !=null and updateDate != &apos;&apos; '> UpdateDate = #{updateDate} ,</if> "
            + " <if test='activityStatus !=null and activityStatus != &apos;&apos; '> ActivityStatus = #{activityStatus} ,</if> "
            + " <if test='eachDayNumber !=null and eachDayNumber != &apos;&apos; '> EachDayNumber = #{eachDayNumber} ,</if> "
            + " <if test='activityRangeMark !=null and activityRangeMark != &apos;&apos; '> ActivityRangeMark = #{activityRangeMark} ,</if> "
            + " <if test='autoFetch !=null and autoFetch != &apos;&apos; '> autoFetch = #{autoFetch} ,</if> "
            + " <if test='codeTotalNum !=null and codeTotalNum != &apos;&apos; '> CodeTotalNum = #{codeTotalNum} ,</if> "
		    + " <if test='consumeIntegralNum !=null and consumeIntegralNum != &apos;&apos; '> ConsumeIntegralNum = #{consumeIntegralNum} ,</if> "
		    + " <if test='activityDesc !=null and activityDesc != &apos;&apos; '> ActivityDesc = #{activityDesc} ,</if> "
            + " </set> "
            + " where Id = #{id}"
    )
    int update(MarketingActivitySet mActivitySet);
}
