package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.*;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;

@Mapper
public interface MarketingActivitySetMapper {
 static String allFields="Id id,ActivityId ActivityId,ActivityTitle ActivityTitle,ActivityStartDate ActivityStartDate,"
 		+ "ActivityEndDate ActivityEndDate,UpdateUserName UpdateUserName,UpdateUserId UpdateUserId,CreateDate createDate,UpdateDate UpdateDate,"
 		+ "ActivityStatus ActivityStatus,EachDayNumber EachDayNumber,ActivityRangeMark ActivityRangeMark,"
 		+ "autoFetch autoFetch,CodeTotalNum codeTotalNum,OrganizationId organizationId,OrganizatioIdlName organizatioIdlName";



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
           + " ActivityRangeMark,autoFetch,CodeTotalNum,CreateDate,UpdateDate) "
           + " VALUES(#{ma.activityId},#{ma.organizationId},#{ma.organizatioIdlName},#{ma.activityTitle},#{ma.activityStartDate},"
           + "#{ma.activityEndDate},#{ma.updateUserId},#{ma.updateUserName},#{ma.activityStatus},#{ma.eachDayNumber},#{ma.activityRangeMark}, "
           + "#{ma.autoFetch},#{ma.codeTotalNum},NOW(),NOW() "
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
}
