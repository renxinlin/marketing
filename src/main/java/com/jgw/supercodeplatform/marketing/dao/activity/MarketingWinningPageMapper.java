package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;

@Mapper
public interface MarketingWinningPageMapper {
    static String allFields="Id id,LoginType loginType,TemplateId templateId,ActivityId activityId";
    
    @Select("select "+allFields+" from marketing_winning_page where ActivityId=#{activityId}")
	MarketingWinningPage getByActivityId(@Param("activityId")Long activityId);

}
