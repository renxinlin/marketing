package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;

@Mapper
public interface MarketingChannelMapper {


	@Insert(" INSERT INTO marketing_channel(ActivitySetId,CustomerName,CustomerCode,CustomerType,CustomerSuperiorType,CustomerSuperior )"
			+ " VALUES(#{activitySetId},#{customerName},#{customerCode},#{customerType},#{customerSuperiorType},#{customerSuperior})")
	void batchInsert(List<MarketingChannel> mList);

}
