package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;

@Mapper
public interface MarketingActivitySetMapper {
 static String allFields="Id id,ActivityId ActivityId,ActivityTitle ActivityTitle,ActivityStartDate ActivityStartDate,ActivityEndDate ActivityEndDate,UpdateUserName UpdateUserName,UpdateUserId UpdateUserId,UpdateDate UpdateDate,"
 		+ "ActivityStatus ActivityStatus,EachDayNumber EachDayNumber,ActivityRangeMark ActivityRangeMark,autoFetch autoFetch";



    @Select("select "+allFields+" from marketing_activity_set where ActivitySetId=#{activitySetId}")
    MarketingActivitySet selectById(Long activitySetId);
 
    @Select("select EachDayNumber from marketing_activity_set where ActivitySetId=#{activitySetId}")
    Integer selectEachDayNumber(Long activitySetId);


    @Update(" <script>"
            + " UPDATE marketing_members "
            + " <set>"
            + " <if test='activityStatus !=null and activityStatus != &apos;&apos; '> ActivityStatus = #{activityStatus} ,</if> "
            + " </set>"
            + " <where> "
            + " <if test='activitySetId !=null and activitySetId != &apos;&apos; '> and ActivitySetId = #{activitySetId} </if>"
            + " </where>"
            + " </script>")
    int updateActivitySetStatus(MarketingActivitySetStatusUpdateParam mUpdateStatus);


   @Insert(" INSERT INTO marketing_activity_set(ActivityId,OrganizationId,OrganizatioIdlName,ActivityTitle,"
           + " ActivityStartDate,ActivityEndDate,UpdateUserId,UpdateUserName,ActivityStatus,EachDayNumber,"
           + " ActivityRangeMark,autoFetch) "
           + " VALUES(#{activityId},#{organizationId},#{organizationFullName},#{activityTitle},#{activityStartDate},"
           + "#{activityEndDate},#{updateUserId},#{updateUserName},#{activityStatus},#{eachDayNumber},#{activityRangeMark}, "
           + "#{autoFetch}"
           + ")")
   int addActivitySet(MarketingActivitySet marketingActivitySet);
}
