package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingSalerActivitySetMO;
import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface MarketingActivitySetMapper extends CommonSql {
 static String allFields="Id id,ActivityId ActivityId,ActivityTitle ActivityTitle,ActivityStartDate ActivityStartDate,"
 		+ "ActivityEndDate ActivityEndDate,UpdateUserName UpdateUserName,UpdateUserId UpdateUserId,CreateDate createDate,UpdateDate UpdateDate,"
 		+ "ActivityStatus ActivityStatus,EachDayNumber EachDayNumber,ActivityRangeMark ActivityRangeMark,"
 		+ "autoFetch autoFetch,CodeTotalNum codeTotalNum,OrganizationId organizationId,OrganizatioIdlName organizatioIdlName,"
 		+ "ActivityDesc activityDesc,ConsumeIntegralNum consumeIntegralNum, ValidCondition validCondition ";

    String whereSearch =
                    "<choose>"
                    + "<when test = 'search == null  or search == &apos;&apos;'>"
                    + "</when>"
                    + "<otherwise>"
                    + "<if test = 'search != null and search != &apos;&apos;'>"
                    + " AND ( "
                    + " mas.ActivityTitle LIKE CONCAT('%', #{search}, '%') "
                    + " OR mas.ActivityStartDate LIKE BINARY CONCAT('%', #{search}, '%') "
                    + " OR mas.ActivityEndDate LIKE BINARY CONCAT('%', #{search}, '%') "
                    + " OR mas.UpdateUserName LIKE CONCAT('%', #{search}, '%') "
                    + " OR mas.OrganizatioIdlName LIKE CONCAT('%', #{search}, '%') "
                    + " OR map.ProductBatchName LIKE CONCAT('%', #{search}, '%') "
                    + " OR map.ProductName LIKE CONCAT('%', #{search}, '%') "
                    + " OR mc.CustomerName LIKE CONCAT('%', #{search}, '%')"
                    + " ) "
                    + "</if>"
                    + "</otherwise>"
                    + "</choose>";

    @Select("select "+allFields+" from marketing_activity_set where Id=#{activitySetId}")
    MarketingActivitySet selectById(Long activitySetId);
 
    @Select("select EachDayNumber from marketing_activity_set where Id=#{activitySetId}")
    Integer selectEachDayNumber(Long activitySetId);


    @Update(" <script>"
            + " UPDATE marketing_activity_set "
            + " <set>"
            + "  ActivityStatus = #{activityStatus} "
            + " </set>"
            + " <where> "
            + "  Id = #{activitySetId} "
            + " </where>"
            + " </script>")
    int updateActivitySetStatus(MarketingActivitySetStatusUpdateParam mUpdateStatus);


   @Insert(" INSERT INTO marketing_activity_set(ActivityId,OrganizationId,OrganizatioIdlName,ActivityTitle,"
           + " ActivityStartDate,ActivityEndDate,UpdateUserId,UpdateUserName,ActivityStatus,EachDayNumber,"
           + " ActivityRangeMark,autoFetch,CodeTotalNum,CreateDate,UpdateDate,ConsumeIntegralNum,ActivityDesc) "
           + " VALUES(#{ma.activityId},#{ma.organizationId},#{ma.organizatioIdlName},#{ma.activityTitle},#{ma.activityStartDate},"
           + "#{ma.activityEndDate},#{ma.updateUserId},#{ma.updateUserName},#{ma.activityStatus},#{ma.eachDayNumber},#{ma.activityRangeMark}, "
           + "#{ma.autoFetch},#{ma.codeTotalNum},NOW(),NOW(),#{ma.consumeIntegralNum} ,#{ma.activityDesc}"
           + ")")
   @Options(useGeneratedKeys=true, keyProperty="ma.id", keyColumn="Id")
   int insert(@Param("ma")MarketingActivitySet marketingActivitySet);

   @Update(" <script>"
           + " UPDATE marketing_activity_set "
           + " <set>"
           + "  CodeTotalNum = #{codeTotalNum} "
           + " </set>"
           + " <where> "
           + " Id = #{id} "
           + " </where>"
           + " </script>")
   void updateCodeTotalNum(@Param("id")Long id,@Param("codeTotalNum") Long codeSum);


    @Select("select "+allFields+" from marketing_activity_set where ActivityTitle=#{activityTitle} and OrganizationId=#{organizationId}")
    MarketingActivitySet selectByTitleOrgId(@Param("activityTitle")String activityTitle, @Param("organizationId")String organizationId);


    @Select("select "+allFields+" from marketing_activity_set where ActivityTitle=#{activityTitle} and OrganizationId=#{organizationId} and id != #{id} ")
    MarketingActivitySet selectByTitleOrgIdWhenUpdate(@Param("activityTitle")String activityTitle,@Param("id")Long activitySetid, @Param("organizationId")String organizationId);

    @Update("update marketing_activity_set set CodeTotalNum =CodeTotalNum+#{codeTotalNum} where Id = #{id}")
   void addCodeTotalNum(@Param("codeTotalNum")Long codeNum, @Param("id")Long activitySetid);



    @Update(" update marketing_activity_set " +
            "<set> "
            + " <if test='activityId !=null and activityId != &apos;&apos; '> ActivityId = #{activityId} ,</if> "
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '> OrganizationId = #{organizationId} ,</if> "
            + " <if test='organizatioIdlName !=null and organizatioIdlName != &apos;&apos; '> OrganizatioIdlName = #{organizatioIdlName} ,</if> "
            + " <if test='activityTitle !=null and activityTitle != &apos;&apos; '> ActivityTitle = #{activityTitle} ,</if> "
            + " <if test='activityStartDate !=null and activityStartDate != &apos;&apos; '> ActivityStartDate = #{activityStartDate} ,</if> "
            + " <if test='activityEndDate !=null and activityEndDate != &apos;&apos; '> ActivityEndDate = #{activityEndDate} ,</if> "
            + " <if test='updateUserId !=null and updateUserId != &apos;&apos; '> UpdateUserId = #{updateUserId} ,</if> "
            + " <if test='updateUserName !=null and updateUserName != &apos;&apos; '> UpdateUserName = #{updateUserName} ,</if> "
            + " <if test='updateDate !=null and updateDate != &apos;&apos; '> UpdateDate = #{updateDate} ,</if> "
            + " <if test='activityStatus !=null and activityStatus != &apos;&apos; '> ActivityStatus = #{activityStatus} ,</if> "
            + " <if test='eachDayNumber !=null and eachDayNumber != &apos;&apos; '> EachDayNumber = #{eachDayNumber} ,</if> "
            + " <if test='activityRangeMark !=null and activityRangeMark != &apos;&apos; '> ActivityRangeMark = #{activityRangeMark} ,</if> "
            + " <if test='autoFetch !=null and autoFetch != &apos;&apos; '> autoFetch = #{autoFetch} ,</if> "
            + " <if test='codeTotalNum !=null and codeTotalNum != &apos;&apos; '> CodeTotalNum = #{codeTotalNum} ,</if> "
		    + " <if test='consumeIntegralNum !=null and consumeIntegralNum != &apos;&apos; '> ConsumeIntegralNum = #{consumeIntegralNum} ,</if> "
		    + " <if test='activityDesc !=null and activityDesc != &apos;&apos; '> ActivityDesc = #{activityDesc} ,</if> "
            + " </set> "
            + " where Id = #{id}"
    )
    int update(MarketingActivitySet mActivitySet);

    @Select(startScript
            + " SELECT mas.Id, mas.ActivityId, mas.ActivityTitle, DATE_FORMAT(mas.ActivityStartDate, '%Y/%m/%d %H:%i') as activityStartDate, "
            + " DATE_FORMAT(mas.ActivityEndDate, '%Y/%m/%d %H:%i') as activityEndDate, mas.UpdateUserId, mas.UpdateUserName, "
            + " DATE_FORMAT(mas.UpdateDate, '%Y/%m/%d %H:%i') as updateDate, "
            + " mas.OrganizatioIdlName, mas.ActivityStatus "
            + " FROM marketing_activity_set mas "
            + " INNER JOIN marketing_activity ma ON ma.Id = mas.ActivityId AND ma.ActivityType = 2 "
            + " LEFT JOIN marketing_activity_product map ON map.ActivitySetId = mas.Id "
            + " LEFT JOIN marketing_channel mc ON mc.ActivitySetId = mas.Id "
            + " WHERE mas.OrganizationId = #{organizationId} "
            + whereSearch
            + " GROUP BY mas.Id "
            + " ORDER BY mas.UpdateDate "
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber}, #{pageSize}</if>"
            + endScript
    )
    @Results({
            @Result(column = "Id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "ActivityId", property = "activityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ActivityTitle", property = "activityTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "activityStartDate", property = "activityStartDate", jdbcType = JdbcType.DATE),
            @Result(column = "activityEndDate", property = "activityEndDate", jdbcType = JdbcType.DATE),
            @Result(column = "UpdateUserName", property = "updateUserName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "UpdateUserId", property = "updateUserId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "updateDate", property = "updateDate", jdbcType = JdbcType.DATE),
            @Result(column = "OrganizatioIdlName", property = "organizationIdName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ActivityStatus", property = "activityStatus", jdbcType = JdbcType.INTEGER),
            @Result(column = "Id", property = "maActivityProducts", javaType = List.class,
                    many = @Many(select = "com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper.selectByActivitySetId"))
})
    List<MarketingSalerActivitySetMO> list(DaoSearchWithOrganizationIdParam searchParams);

    @Select(startScript
            + " SELECT COUNT(*)"
            + " FROM marketing_activity_set mas "
            + " INNER JOIN marketing_activity ma ON ma.Id = mas.ActivityId AND ma.ActivityType = 2 "
            + " LEFT JOIN marketing_activity_product map ON map.ActivitySetId = mas.Id "
            + " LEFT JOIN marketing_channel mc ON mc.ActivitySetId = mas.Id "
            + " WHERE mas.OrganizationId = #{organizationId} "
            + whereSearch
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber}, #{pageSize}</if>"
            + endScript
    )
    int count(DaoSearchWithOrganizationIdParam searchParams);

    @Update(" UPDATE marketing_activity_set SET ActivityStatus = #{mas.activityStatus}, UpdateUserId = #{userId}, " +
            "UpdateUserName = #{userName}, UpdateDate = NOW() WHERE Id = #{mas.activitySetId} and ActivityId = 3 ")
    void updateSalerActivitySetStatus(@Param("mas") MarketingActivitySetStatusUpdateParam setStatusUpdateParam, @Param("userId") String userId, @Param("userName") String userName);
}
