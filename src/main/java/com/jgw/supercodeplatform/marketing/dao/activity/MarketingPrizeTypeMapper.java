package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MarketingPrizeTypeMapper {



	@Insert({
			"<script>",
			"INSERT INTO marketing_prize_type(ActivitySetId,PrizeTypeName,PrizeAmount,PrizeProbability,RandomAmount ) VALUES ",
			"<foreach collection='mList' item='mPrize' index='index' separator=','>",
			"(#{mPrize.activitySetId},#{mPrize.prizeTypeName},#{mPrize.prizeAmount},#{mPrize.prizeProbability},#{mPrize.randomAmount})",
			"</foreach>",
			"</script>"
	})
	void batchInsert(@Param(value="mList")List<MarketingPrizeType> mList);

	List<MarketingPrizeType> selectByActivitySetId(Long activitySetId);

	void update(MarketingPrizeType marketingPrizeType);

}
