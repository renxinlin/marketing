package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;

@Mapper
public interface MarketingChannelMapper {

	void batchInsert(List<MarketingChannel> mList);

}
