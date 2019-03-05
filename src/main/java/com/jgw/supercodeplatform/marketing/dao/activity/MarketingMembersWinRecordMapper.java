package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordAddParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;

@Mapper
public interface MarketingMembersWinRecordMapper extends CommonSql{
	static String allFields="Id id,ActivityType activityType,MemberName memberName,NickName nickName,Openid openid,PrizeTypeId prizeTypeId,PrizeTypeName prizeTypeName,"
			+ "WinningAmount winningAmount,WinningCode winningCode,Mobile mobile,Dealer dealer,Store store,OrganizationId organizationId";
	static String whereSearch =
			"<where>" +
					"<choose>" +
					//当search为空时要么为高级搜索要么没有搜索暂时为不搜索
					"<when test='search == null or search == &apos;&apos;'>" +
					
					"</when>" +
					//如果search不为空则普通搜索
					"<otherwise>" +
						"<if test='search !=null and search != &apos;&apos;'>" +
						" AND (" +
						" ActivityType LIKE CONCAT('%',#{search},'%')  " +
						" OR MemberName LIKE CONCAT('%',#{search},'%') " +
						" OR NickName LIKE CONCAT('%',#{search},'%') " +
						" OR Openid LIKE CONCAT('%',#{search},'%') " +
						" OR Mobile LIKE CONCAT('%',#{search},'%') " +
						" OR PrizeTypeName LIKE CONCAT('%',#{search},'%') " +
						" OR WinningAmount LIKE CONCAT('%',#{search},'%') " +
						" OR WinningCode LIKE CONCAT('%',#{search},'%') " +
						" OR Dealer LIKE CONCAT('%',#{search},'%') " +
						" OR Store LIKE CONCAT('%',#{search},'%')" +
						")" +
						"</if>" +
					"</otherwise>" +
					"</choose>" +
					"<if test='organizationId !=null and organizationId != &apos;&apos; '> AND trace_funtemplatestatistical.organizationId = #{organizationId}</if>"+
					"</where>";

	
	@Select(startScript
			+"select count(*) from marketing_members_win "
			+whereSearch
			+endScript
		   )
	int count(MarketingMembersWinRecordListParam searchParams);

	@Select(startScript
			+"select "+allFields+" from marketing_members_win "
			+whereSearch
			+ " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
			+endScript
		   )
	List<MarketingMembersWinRecord> list(MarketingMembersWinRecordListParam searchParams);

	@Insert(" INSERT INTO marketing_activity_set(ActivityType,MemberName,NickName,Openid,"
			+ " PrizeTypeId,PrizeTypeName,WinningAmount,WinningCode,OrganizationId,Mobile,"
			+ " Dealer,Store) "
			+ " VALUES(#{activityType},#{memberName},#{nickName},#{openid},#{prizeTypeId},"
			+ "#{prizeTypeName},#{winningAmount},#{winningCode},#{organizationId},#{mobile},#{dealer}, "
			+ "#{store}"
			+ ")")
	int addWinRecord(MarketingMembersWinRecordAddParam winRecordAddParam);


	@Select({
			startScript,
			allFields,
			"from marketing_members_win ",
			"where Id ",
			"in(",
			"<foreach collection = 'list' item = 'id' separator = ','>",
			"#{id}",
			"</foreach>)",
			endScript
	})
	List<MarketingMembersWinRecord> getWinRecordByidArray(List<String> ids);

}
