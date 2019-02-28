package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;

@Mapper
public interface MarketingPrizeTypeMapper {

	@Insert(" INSERT INTO marketing_prize_type(ActivitySetId,PrizeTypeName,PrizeAmount,PrizeProbability,RandomAmount )"
			+ " VALUES(#{activitySetId},#{prizeTypeName},#{prizeAmount},#{prizeProbability},#{randomAmount})")
	void batchInsert(List<MarketingPrizeType> mList);

}
