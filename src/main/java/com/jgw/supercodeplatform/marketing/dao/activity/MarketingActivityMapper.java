package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityListParam;
import org.apache.ibatis.annotations.*;

import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingActivityListMO;
import com.jgw.supercodeplatform.marketing.dao.CommonSql;
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
						" OR ActivityTitle LIKE CONCAT('%',#{search},'%') " +
						" OR ActivityStartDate LIKE CONCAT('%',#{search},'%') " +
						" OR ActivityEndDate LIKE CONCAT('%',#{search},'%') " +
						" OR CustomerName LIKE CONCAT('%',#{search},'%') " +
					    " OR CodeType LIKE CONCAT('%',#{search},'%') " +
						" OR ProductBatchId LIKE CONCAT('%',#{search},'%') " +
						" OR ProductBatchName LIKE CONCAT('%',#{search},'%') " +
						" OR ProductId LIKE CONCAT('%',#{search},'%') " +
						" OR ProductName LIKE CONCAT('%',#{search},'%') " +
					    " OR UpdateUserName LIKE CONCAT('%',#{search},'%') " +
					    " OR UpdateDate LIKE CONCAT('%',#{search},'%') " +
					    " OR ActivityStatus LIKE CONCAT('%',#{search},'%') " +
						")" +
						"</if>" +
					"</otherwise>" +
					"</choose>" +
					" <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"+
					"</where>";
    
	@Select("select "+allFileds +" from marketing_activity")
	List<MarketingActivity> selectAll();

	@Select(startScript
			+ " select aset.Id id, ac.ActivityType activityType , ac.ActivityName activityName,aset.ActivityTitle activityTitle,aset.ActivityStartDate activityStartDate,"
			+ " aset.ActivityEndDate activityEndDate,aset.UpdateUserName updateUserName,aset.UpdateDate updateDate,aset.ActivityStatus activityStatus,"
			+ " ap.ProductBatchName productBatchName,ap.ProductName productName,ap.ProductId productId,"
			+ " ap.ProductBatchId productBatchId,mc.CustomerName customerName "
			+ " from  marketing_activity_set aset left join  marketing_activity ac on aset.ActivityId=ac.Id  "
			+ " left join marketing_activity_product ap on aset.Id=ap.ActivitySetId "
			+ " left join marketing_channel mc on aset.Id=mc.ActivitySetId "
			+whereSearch
			+ " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
			+endScript)
	List<MarketingActivityListMO> list(MarketingActivityListParam marketingActivityListParam);

	@Select(startScript
			+"select count(*) from "
			+ "("
			 + "select count(*)"
			 + "from  marketing_activity_set aset left join  marketing_activity ac on aset.ActivityId=ac.Id "
			 + "left join marketing_activity_product ap on aset.Id=ap.ActivitySetId "
			 + "left join marketing_channel mc on aset.Id=mc.ActivitySetId "
			 +whereSearch
			 +"group by aset.Id"
			 + ")a"
			+endScript)
	int count(MarketingActivityListParam marketingActivityListParam);



	@Insert(" INSERT INTO marketing_activity(ActivityType,ActivityName)"
			+ " VALUES(#{ma.activityType},#{ma.activityName} )")
	@Options(useGeneratedKeys=true, keyProperty="ma.id", keyColumn="Id")
	int addActivity(@Param("ma") MarketingActivity marketingActivity);

	@Select("select "+allFileds +" from marketing_activity where Id=#{activityId}")
	MarketingActivity selectById(@Param("activityId")Long activityId);



}
