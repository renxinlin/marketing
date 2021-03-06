package com.jgw.supercodeplatform.marketing.dao.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import com.jgw.supercodeplatform.marketing.dto.platform.JoinResultPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface MarketingMembersWinRecordMapper extends CommonSql{

	static String allFields="Id as id,ActivityId as activityId,ActivitySetId as activitySetId,ActivityName as activityName,Openid as openid,PrizeTypeId as prizeTypeId,CreateTime createTime,UpdateTime updateTime,TradeNo tradeNo,"
			+ "WinningAmount as winningAmount,WinningCode as winningCode,Mobile as mobile,OrganizationId as organizationId,OrganizationFullName organizationFullName,PrizeName prizeName,ProductId productId, AwardGrade awardGrade";

	static String allWinFields="mmw.Id as id,mmw.ActivityId as activityId,mmw.ActivitySetId as activitySetId,mmw.ActivityName as activityName,mmw.Openid as openid,mmw.PrizeTypeId as prizeTypeId,"
			+ " CAST(mmw.WinningAmount AS CHAR) as winningAmount,mmw.PrizeName as prizeName "
			+ ",mmw.WinningCode as winningCode,mmw.Mobile as mobile,mmw.OrganizationId as organizationId,"
			+ "mm.UserName as userName,mm.WxName as wxName,mm.CustomerName as customerName, "
			+ "mpt.PrizeTypeName as prizeTypeName,map.ProductName as productName,mpt.AwardType awardType ,mmw.ProductId productId";

	static String whereSearch =
			"<where>" +
					"<choose>" +
					//当search为空时要么为高级搜索要么没有搜索暂时为不搜索
					"<when test='search == null or search == &apos;&apos;'>" +
//                    " <if test = ' activityName != null and activityName != &apos;&apos; '> mmw.ActivityName = #{activityName}</if>" +
//                    " <if test = ' userName != null and userName != &apos;&apos; '> mm.UserName  = #{userName}</if>" +
//                    " <if test = ' wxName != null and wxName != &apos;&apos; '> WxName = #{wxName}</if>" +
//                    " <if test = ' openid != null and openid != &apos;&apos; '> mmw.Openid  = #{openid}</if>" +
//                    " <if test = ' mobile != null and mobile != &apos;&apos; '> mmw.Mobile = #{mobile}</if>" +
//                    " <if test = ' prizeTypeName != null and prizeTypeName != &apos;&apos; '> mpt.PrizeTypeName = #{prizeTypeName}</if>" +
//                    " <if test = ' winningAmount != null and  winningAmount != &apos;&apos; '> mmw.WinningAmount = #{winningAmount}</if>" +
//                    " <if test = ' winningCode != null and winningCode != &apos;&apos; '> mmw.WinningCode = #{winningCode}</if>" +
//                    " <if test = ' productName != null and productName != &apos;&apos; '> map.ProductName = #{productName}</if>" +
                    "</when>" +
					//如果search不为空则普通搜索
					"<otherwise>" +
						"<if test='search !=null and search != &apos;&apos;'>" +
						" AND (" +
						" mmw.ActivityName LIKE CONCAT('%',#{search},'%')  " +
						" OR mm.UserName LIKE CONCAT('%',#{search},'%') " +
						" OR WxName LIKE CONCAT('%',#{search},'%') " +
						" OR mmw.Openid LIKE CONCAT('%',#{search},'%') " +
						" OR mmw.Mobile LIKE CONCAT('%',#{search},'%') " +
						" OR mpt.PrizeTypeName LIKE CONCAT('%',#{search},'%') " +
                        " OR mmw.WinningAmount LIKE CONCAT('%',#{search},'%') " +
                        " OR mmw.WinningCode LIKE CONCAT('%',#{search},'%') " +
                    	" OR mmw.PrizeName LIKE CONCAT('%',#{search},'%') " +
						" OR map.ProductName LIKE CONCAT('%',#{search},'%') " +
						")" +
						"</if>" +
					"</otherwise>" +
					"</choose>" +
					"<if test='organizationId !=null and organizationId != &apos;&apos; '> AND mmw.OrganizationId = #{organizationId}</if>"+
					"<if test='activitySetId !=null and activitySetId != &apos;&apos; '> AND mmw.ActivitySetId = #{activitySetId}</if>"+
					"</where>";

	
	@Select(startScript
			+"select count(*) from "
			+"marketing_members_win mmw left join marketing_members mm on mmw.Openid = mm.Openid AND mmw.OrganizationId = mm.OrganizationId  "
			+"left join marketing_prize_type mpt on mmw.PrizeTypeId = mpt.Id left join marketing_activity_product map on mmw.ActivitySetId = map.ActivitySetId and mmw.ProductId=map.ProductId AND map.ProductBatchId = mmw.ProductBatchId "
			+whereSearch
			+endScript
		   )
	int count(MarketingMembersWinRecordListParam searchParams);

	@Select(startScript
			+"select "+allWinFields+" from "
			+"marketing_members_win mmw left join marketing_members mm on mmw.Openid = mm.Openid AND mmw.OrganizationId = mm.OrganizationId  "
			+"left join marketing_prize_type mpt on mmw.PrizeTypeId = mpt.Id left join marketing_activity_product map on mmw.ActivitySetId = map.ActivitySetId and mmw.ProductId=map.ProductId AND map.ProductBatchId = mmw.ProductBatchId "
			+whereSearch
			+ " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
			+endScript
		   )
	List<MarketingMembersWinRecordListReturn> list(MarketingMembersWinRecordListParam searchParams);

	@Insert(" INSERT INTO marketing_members_win(ActivityId,ActivitySetId,ActivityName,Openid,CreateTime,UpdateTime,TradeNo,"
			+ " PrizeTypeId,WinningAmount,WinningCode,OrganizationId,OrganizationFullName,Mobile,PrizeName,ProductId,ProductBatchId,AwardGrade)"
			+ " VALUES(#{activityId},#{activitySetId},#{activityName},#{openid},NOW(),NOW(),#{tradeNo},#{prizeTypeId},"
			+ "#{winningAmount},#{winningCode},#{organizationId},#{organizationFullName},#{mobile},#{prizeName},"
			+ "#{productId},#{productBatchId},#{awardGrade})")
	int addWinRecord(MarketingMembersWinRecord winRecord);

	@Select("SELECT "+allFields+" FROM marketing_members_win WHERE WinningCode = #{winningCode}")
	MarketingMembersWinRecord getRecordByCodeId(@Param("winningCode") String winningCode);


	@Select({startScript,
			"SELECT "+allFields+" FROM marketing_members_win ",
			"<where> ",
			"ActivitySetId = #{activitySetId} ",
			"<if test='search !=null and search != &apos;&apos;'> AND (",
			"WinningCode LIKE CONCAT('%',#{search},'%') OR ",
			"PrizeName LIKE CONCAT('%',#{search},'%') OR ",
			"OrganizationFullName LIKE CONCAT('%',#{search},'%') ",
			") </if></where>",
			" ORDER BY Id DESC",
			"<if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>",
			endScript})
	List<MarketingMembersWinRecord> listWinRecord(JoinResultPage joinResultPage);

	@Select({startScript,
			"SELECT COUNT(1) FROM marketing_members_win ",
			"<where> ",
			"ActivitySetId = #{activitySetId} ",
			"<if test='search !=null and search != &apos;&apos;'> AND (",
			"WinningCode LIKE CONCAT('%',#{search},'%') OR ",
			"PrizeName LIKE CONCAT('%',#{search},'%') OR ",
			"OrganizationFullName LIKE CONCAT('%',#{search},'%') ",
			") </if></where>",
			"<if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>",
			endScript})
	int countWinRecord(JoinResultPage joinResultPage);

	@Select("SELECT COUNT(1) FROM marketing_members_win WHERE ActivityId = 5 AND CreateTime >= #{createTimeStart} AND CreateTime <= #{createTimeEnd}")
	long countPlatformTotal(@Param("createTimeStart") Date createTimeStart, @Param("createTimeEnd") Date createTimeEnd);

	@Select("SELECT COUNT(1) FROM marketing_members_win WHERE ActivityId = 5 AND WinningAmount > 0 AND CreateTime >= #{createTimeStart} AND CreateTime < #{createTimeEnd}")
	long countPlatformWining(@Param("createTimeStart") Date createTimeStart, @Param("createTimeEnd") Date createTimeEnd);

	@Select("SELECT "+allFields+" FROM marketing_members_win WHERE ActivitySetId = #{activitySetId} AND Openid = #{openid} AND AwardGrade = 1")
	MarketingMembersWinRecord getFirstAward(@Param("activitySetId") Long activitySetId, @Param("openid") String openid);

	@Select({"SELECT COUNT(*) FROM (select Openid from marketing_members_win WHERE CreateTime >= #{startTime} AND CreateTime < #{endTime} GROUP BY Openid) a ",
			"INNER JOIN marketing_members b ON a.Openid = b.Openid WHERE b.State <> 2 AND b.RegistDate >= #{startTime} AND b.RegistDate < #{endTime}"})
	long countActUser(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
