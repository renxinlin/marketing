package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityListParam;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

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
//					" <if test = '  != null and  != &apos;&apos; '> aset.ActivityTitle = #{}</if>" +


					"</when>" +
					//如果search不为空则普通搜索
					"<otherwise>" +
						"<if test='search !=null and search != &apos;&apos;'>" +
						" AND (" +
						" aset.ActivityTitle LIKE CONCAT('%',#{search},'%') " +
						" OR aset.ActivityStartDate LIKE binary  CONCAT('%',#{search},'%') " +
						" OR aset.ActivityEndDate LIKE binary  CONCAT('%',#{search},'%') " +
						" OR ap.ProductBatchName LIKE CONCAT('%',#{search},'%') " +
						" OR ap.ProductName LIKE CONCAT('%',#{search},'%') " +
					    " OR aset.UpdateUserName LIKE CONCAT('%',#{search},'%') " +
					    " OR aset.UpdateDate LIKE binary  CONCAT('%',#{search},'%') " +
						")" +
						"</if>" +
					"</otherwise>" +
					"</choose>" +
					" <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"+
					"</where>";
    
	@Select("select "+allFileds +" from marketing_activity")
	List<MarketingActivity> selectAll();

	@Select(startScript
			+ " select aset.Id id, ac.ActivityName activityName,aset.ActivityTitle activityTitle, "
			+ " DATE_FORMAT(aset.ActivityStartDate,'%Y-%m-%d') as activityStartDate, "
			+ " DATE_FORMAT(aset.ActivityEndDate,'%Y-%m-%d') as activityEndDate, "
			+ " aset.UpdateUserName updateUserName, "
			+ " DATE_FORMAT(aset.UpdateDate,'%Y-%m-%d %H:%i:%S') as updateDate,"

			+ " aset.ActivityStatus activityStatus,"
			+ " ap.ProductBatchName productBatchName,ap.ProductName productName,ap.ProductId productId,"
			+ " ap.ProductBatchId productBatchId,mc.CustomerName customerName "
			+ " from  marketing_activity_set aset left join  marketing_activity ac on aset.ActivityId=ac.Id  "
			+ " left join marketing_activity_product ap on aset.Id=ap.ActivitySetId "
			+ " left join marketing_channel mc on aset.Id=mc.ActivitySetId "
			+whereSearch
			+"group by aset.Id"
			+ " ORDER BY UpdateDate DESC"
			+ " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"

			+endScript)
    @Results({
        @Result(column = "Id", property = "id", jdbcType = JdbcType.BIGINT),
        @Result(column = "ActivityName", property = "activityName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "ActivityTitle", property = "activityTitle", jdbcType = JdbcType.VARCHAR),
        @Result(column = "ActivityStartDate", property = "activityStartDate", jdbcType = JdbcType.DATE),
        @Result(column = "ActivityEndDate", property = "activityEndDate", jdbcType = JdbcType.DATE),
        @Result(column = "UpdateUserName", property = "updateUserName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "UpdateDate", property = "updateDate", jdbcType = JdbcType.DATE),
        @Result(column = "ActivityStatus", property = "activityStatus", jdbcType = JdbcType.VARCHAR),
        @Result(column = "Id", property = "marketingChannels", javaType = List.class,
                many = @Many(select = "com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper.selectByActivitySetId")),
        @Result(column = "Id", property = "maActivityProducts", javaType = List.class,
                many = @Many(select = "com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper.selectByActivitySetId"))
      })
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
