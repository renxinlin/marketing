package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;

@Mapper
public interface MarketingPrizeTypeMapper {

	String allFields =" Id id,ActivitySetId activitySetId,PrizeTypeName prizeTypeName,PrizeAmount prizeAmount,PrizeProbability prizeProbability,IsRrandomMoney isRrandomMoney,"
			+"WiningNum winingNum,RealPrize realPrize,LowRand lowRand,HighRand highRand ";



	@Insert({
			"<script>",
			"INSERT INTO marketing_prize_type(ActivitySetId,PrizeTypeName,PrizeAmount,PrizeProbability,IsRrandomMoney,WiningNum,RealPrize,LowRand,HighRand ) VALUES ",
			"<foreach collection='mList' item='mPrize' index='index' separator=','>",
			"(#{mPrize.activitySetId},#{mPrize.prizeTypeName},#{mPrize.prizeAmount},#{mPrize.prizeProbability},#{mPrize.isRrandomMoney},#{mPrize.winingNum},"
			+ "#{mPrize.realPrize},#{mPrize.lowRand},#{mPrize.highRand})",
			"</foreach>",
			"</script>"
	})
	void batchInsert(@Param(value="mList")List<MarketingPrizeType> mList);

	@Select("select "+allFields+" from marketing_prize_type where ActivitySetId=#{activitySetId}")
	List<MarketingPrizeType> selectByActivitySetId(@Param("activitySetId") Long activitySetId);

	@Select("select "+allFields+" from marketing_prize_type where Id=#{id}")
	MarketingPrizeType selectById(@Param("id") Long id);

	@Update(" <script>"
			+ " UPDATE marketing_prize_type "
			+ " <set>"
			+ " <if test='prizeTypeName !=null and prizeTypeName != &apos;&apos; '> PrizeTypeName = #{prizeTypeName} ,</if> "
			+ " <if test='activitySetId !=null and activitySetId != &apos;&apos; '> ActivitySetId = #{activitySetId} ,</if> "
			+ " <if test='prizeAmount !=null and prizeAmount != &apos;&apos; '> PrizeAmount = #{prizeAmount} ,</if> "
			+ " <if test='prizeProbability !=null and prizeProbability != &apos;&apos; '> PrizeProbability = #{prizeProbability} ,</if> "
			+ " <if test='isRrandomMoney !=null and isRrandomMoney != &apos;&apos; '> IsRrandomMoney = #{isRrandomMoney} ,</if> "
			+ " <if test='winingNum !=null and winingNum != &apos;&apos; '> WiningNum = #{winingNum} ,</if> "
			+ " <if test='realPrize !=null and realPrize != &apos;&apos; '> RealPrize = #{realPrize} ,</if> "
			+ " <if test='lowRand !=null and lowRand != &apos;&apos; '> LowRand = #{lowRand} ,</if> "
			+ " <if test='highRand !=null and highRand != &apos;&apos; '> HighRand = #{highRand} ,</if> "
			+ " </set>"
			+ " <where> "
			+ " <if test='id !=null and id != &apos;&apos; '> and Id = #{id} </if>"
			+ " </where>"
			+ " </script>")
	void update(MarketingPrizeType marketingPrizeType);

}
