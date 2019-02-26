package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;

@Mapper
public interface MarketingPrizeTypeMapper {

	void batchInsert(List<MarketingPrizeType> mList);

}
