package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MarketingActivitySetMapper {
 static String allFields="Id id,ActivityId ActivityId,ActivityTitle ActivityTitle,ActivityStartDate ActivityStartDate,ActivityEndDate ActivityEndDate,UpdateUserName UpdateUserName,UpdateUserId UpdateUserId,UpdateDate UpdateDate,"
 		+ "ActivityStatus ActivityStatus,EachDayNumber EachDayNumber,EachMostNumber EachMostNumber,ActivityRangeMark ActivityRangeMark,autoFetch autoFetch";

    @Select("select "+allFields+" from marketing_activity_set where ActivitySetId=#{activitySetId}")
    MarketingActivitySet selectById(Long activitySetId);
 
 
}
