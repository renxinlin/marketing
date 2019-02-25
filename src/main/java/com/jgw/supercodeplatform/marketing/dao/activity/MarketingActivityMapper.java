package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingActivityListMO;
import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;

@Mapper
public interface MarketingActivityMapper extends CommonSql{
    static String allFileds="Id id,ActivityType activityType,ActivityName activityName";
    
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
    
	@Select("select "+allFileds +" from marketing_activity")
	List<MarketingActivity> selectAll();

	@Select(startScript
			+"select aset.Id id,  ac.ActivityName activityName,aset.ActivityTitle activityTitle,aset.ActivityStartDate activityStartDate,"
			+ "aset.ActivityEndDate activityEndDate,aset.UpdateUserName updateUserName,aset.UpdateDate updateDate,aset.ActivityStatus activityStatus,"
			+ "ap.ProductBatchName productBatchName,ap.ProductName productName,ap.ProductId productId,"
			+ "ap.ProductBatchId productBatchId"
			+ "from  marketing_activity_set aset left join  marketing_activity ac on aset.ActivityId=ac.Id "
			+ "left join marketing_activity_product ap on aset.Id=ap.ActivitySetId"
			+whereSearch
			+ " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
			+endScript)
	List<MarketingActivityListMO> list(MarketingMembersWinRecordListParam searchParams);

	@Select(startScript
			+"select count(*) from "
			+ "("
			 + "select count(*)"
			 + "from  marketing_activity_set aset left join  marketing_activity ac on aset.ActivityId=ac.Id "
			 + "left join marketing_activity_product ap on aset.Id=ap.ActivitySetId"
			 +whereSearch
			 +"group by aset.Id"
			 + ")a"
			+endScript)
	int count(MarketingMembersWinRecordListParam searchParams);

}
