package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketingPrizeTypeMapper {

	String allFields =" Id id,ActivitySetId activitySetId,PrizeTypeName prizeTypeName,PrizeAmount prizeAmount,PrizeProbability prizeProbability,IsRrandomMoney isRrandomMoney,"
			+"WiningNum winingNum,RealPrize realPrize,LowRand lowRand,HighRand highRand,AwardType awardType,RemainingStock remainingStock,CardLink cardLink,"
			+ "AwardIntegralNum awardIntegralNum";



	@Insert({
			"<script>",
			"INSERT INTO marketing_prize_type(ActivitySetId,PrizeTypeName,PrizeAmount,PrizeProbability,IsRrandomMoney,WiningNum,RealPrize,LowRand,HighRand,AwardType, RemainingStock,"
			+ "CardLink,AwardIntegralNum) VALUES ",
			"<foreach collection='mList' item='mPrize' index='index' separator=','>",
			"(#{mPrize.activitySetId},#{mPrize.prizeTypeName},#{mPrize.prizeAmount},#{mPrize.prizeProbability},#{mPrize.isRrandomMoney},#{mPrize.winingNum},"
			+ "#{mPrize.realPrize},#{mPrize.lowRand},#{mPrize.highRand},#{mPrize.awardType},#{mPrize.remainingStock},#{mPrize.cardLink},#{mPrize.awardIntegralNum})",
			"</foreach>",
			"</script>"
	})
	void batchInsert(@Param(value="mList")List<MarketingPrizeType> mList);

	@Select("select "+allFields+" from marketing_prize_type where ActivitySetId=#{activitySetId} and RealPrize=1")
	List<MarketingPrizeType> selectByActivitySetId(@Param("activitySetId") Long activitySetId);

	@Select("select "+allFields+" from marketing_prize_type where Id=#{id}")
	MarketingPrizeType selectById(@Param("id") Long id);

	@Update(" <script>"
			+ " UPDATE marketing_prize_type "
			+ " <set>"
			+ " <if test='prizeTypeName !=null and prizeTypeName != &apos;&apos; '> PrizeTypeName = #{prizeTypeName} ,</if> "
			+ " <if test='activitySetId !=null and activitySetId != &apos;&apos; '> ActivitySetId = #{activitySetId} ,</if> "
			+ " <if test='prizeAmount !=null  '> PrizeAmount = #{prizeAmount} ,</if> "
			+ " <if test='prizeProbability !=null  '> PrizeProbability = #{prizeProbability} ,</if> "
			+ " <if test='isRrandomMoney !=null '> IsRrandomMoney = #{isRrandomMoney} ,</if> "
			+ " <if test='winingNum !=null  '> WiningNum = #{winingNum} ,</if> "
			+ " <if test='realPrize !=null   '> RealPrize = #{realPrize} ,</if> "
			+ " <if test='lowRand !=null   '> LowRand = #{lowRand} ,</if> "
			+ " <if test='highRand !=null '> HighRand = #{highRand} ,</if> "
			+ " <if test='awardType !=null   '> AwardType = #{awardType} ,</if> "
			+ " <if test='remainingStock !=null   '> RemainingStock = #{remainingStock} ,</if> "
			+ " <if test='cardLink !=null and cardLink != &apos;&apos;'> CardLink = #{cardLink} ,</if> "
			+ " <if test='awardIntegralNum !=null'> AwardIntegralNum = #{awardIntegralNum} ,</if> "
			+ " </set>"
			+ " where Id = #{id} "
			+ " </script>")
	void update(MarketingPrizeType marketingPrizeType);

	@Select("select "+allFields+" from marketing_prize_type where ActivitySetId=#{activitySetId}")
	List<MarketingPrizeType> selectByActivitySetIdIncludeUnreal(Long activitySetId);
	
	@Select("select "+allFields+" from marketing_prize_type where ActivitySetId=#{activitySetId}")
	List<MarketingPrizeTypeMO> selectMOByActivitySetIdIncludeUnreal(Long activitySetId);

	@Delete(" delete from marketing_prize_type where  ActivitySetId=#{activitySetId} ")
	void deleteByActivitySetId(Long id);

	@Update(" <script>"
			+ " UPDATE marketing_prize_type set RemainingStock = RemainingStock-1 where Id = #{id} "
			+ " </script>")
	void updateRemainingStock(@Param("id")long id);
}
