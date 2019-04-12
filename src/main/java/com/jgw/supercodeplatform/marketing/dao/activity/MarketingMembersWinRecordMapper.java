package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;

@Mapper
public interface MarketingMembersWinRecordMapper extends CommonSql{

	static String allFields="Id as id,ActivityId as activityId,ActivitySetId as activitySetId,ActivityName as activityName,Openid as openid,PrizeTypeId as prizeTypeId,"
			+ "WinningAmount as winningAmount,WinningCode as winningCode,Mobile as mobile,OrganizationId as organizationId ";

	static String allWinFields="mmw.Id as id,mmw.ActivityId as activityId,mmw.ActivitySetId as activitySetId,mmw.ActivityName as activityName,mmw.Openid as openid,mmw.PrizeTypeId as prizeTypeId,"
			+ " CAST(mmw.WinningAmount AS CHAR) as winningAmount "
			+ ",mmw.WinningCode as winningCode,mmw.Mobile as mobile,mmw.OrganizationId as organizationId,"
			+ "mm.UserName as userName,mm.WxName as wxName,mm.CustomerName as customerName, "
			+ "mpt.PrizeTypeName as prizeTypeName,map.ProductName as productName ";

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
			+"left join marketing_prize_type mpt on mmw.PrizeTypeId = mpt.Id left join marketing_activity_product map on mmw.ActivitySetId = map.ActivitySetId "
			+whereSearch
			+endScript
		   )
	int count(MarketingMembersWinRecordListParam searchParams);

	@Select(startScript
			+"select "+allWinFields+" from "
			+"marketing_members_win mmw left join marketing_members mm on mmw.Openid = mm.Openid AND mmw.OrganizationId = mm.OrganizationId  "
			+"left join marketing_prize_type mpt on mmw.PrizeTypeId = mpt.Id left join marketing_activity_product map on mmw.ActivitySetId = map.ActivitySetId  "
			+whereSearch
			+ " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
			+endScript
		   )
	List<MarketingMembersWinRecordListReturn> list(MarketingMembersWinRecordListParam searchParams);

	@Insert(" INSERT INTO marketing_members_win(ActivityId,ActivitySetId,ActivityName,Openid,"
			+ " PrizeTypeId,WinningAmount,WinningCode,OrganizationId,Mobile)"
			+ " VALUES(#{activityId},#{activitySetId},#{activityName},#{openid},#{prizeTypeId},"
			+ "#{winningAmount},#{winningCode},#{organizationId},#{mobile} "
			+ ")")
	int addWinRecord(MarketingMembersWinRecord winRecord);


	@Update(startScript
			+"update marketing_members_win "
				+ " <set>"
			+ " <if test='newopenId !=null and newopenId != &apos;&apos; '> OpenId = #{newopenId} ,</if> "
			+ " <if test='mobile !=null and mobile != &apos;&apos; '> Mobile = #{mobile} ,</if> "
			+ " </set>"
			+"where OrganizationId = #{organizationId} and OpenId= #{oldopenId}"
			+endScript)
	void updateOpenIdAndMobileByOpenIdAndOrgId(@Param("newopenId")String newopenId, @Param("mobile")String mobile,  @Param("organizationId")String organizationId,
			 @Param("oldopenId")String oldopenId);

}
