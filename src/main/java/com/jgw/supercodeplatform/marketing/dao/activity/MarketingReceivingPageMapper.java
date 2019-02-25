package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;

@Mapper
public interface MarketingReceivingPageMapper {
    static String allFields="";
	MarketingReceivingPage getByActivityId(Long activityId);

}
