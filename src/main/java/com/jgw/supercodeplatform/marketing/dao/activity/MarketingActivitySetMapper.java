package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithUser;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformActivityVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingSalerActivitySetMO;
import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;

@Mapper
public interface MarketingActivitySetMapper extends CommonSql {
 static String allFields="Id id,ActivityId ActivityId,ActivityTitle ActivityTitle,ActivityStartDate ActivityStartDate,"
 		+ "ActivityEndDate ActivityEndDate,UpdateUserName UpdateUserName,UpdateUserId UpdateUserId,CreateDate createDate,UpdateDate UpdateDate,"
 		+ "ActivityStatus ActivityStatus,ActivityRangeMark ActivityRangeMark,"
 		+ "autoFetch autoFetch,CodeTotalNum codeTotalNum,OrganizationId organizationId,OrganizatioIdlName organizatioIdlName,"
 		+ "ActivityDesc activityDesc, ValidCondition validCondition, SendAudit sendAudit, MerchantsInfo, merchantsInfo ";

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


    @Select("select "+allFields+" from marketing_activity_set where Id=#{activitySetId} and ActivityId = #{activityId}")
    MarketingActivitySet selectByIdWithActivityId(Long activitySetId, Long activityId);

    @Select("select "+allFields+" from marketing_activity_set where ActivityTitle=#{activityTitle} and OrganizationId=#{organizationId} and ActivityId = #{activityId}")
    MarketingActivitySet selectByTitleOrgIdWithActivityId(@Param("activityTitle")String activityTitle, @Param("organizationId")String organizationId,@Param("activityId")Byte activityId);


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


   @Insert(" INSERT INTO marketing_activity_set(ActivityId,OrganizationId,OrganizatioIdlName,ActivityTitle, "
           + " ActivityStartDate,ActivityEndDate,UpdateUserId,UpdateUserName,ActivityStatus, "
           + " ActivityRangeMark,autoFetch,CodeTotalNum,CreateDate,UpdateDate,ActivityDesc,ValidCondition,SendAudit,MerchantsInfo) "
           + " VALUES(#{ma.activityId},#{ma.organizationId},#{ma.organizatioIdlName},#{ma.activityTitle},#{ma.activityStartDate}, "
           + " #{ma.activityEndDate},#{ma.updateUserId},#{ma.updateUserName},#{ma.activityStatus},#{ma.activityRangeMark}, "
           + " #{ma.autoFetch},#{ma.codeTotalNum},NOW(),NOW(), #{ma.activityDesc},#{ma.validCondition},#{ma.sendAudit},#{ma.merchantsInfo} "
           + " )")
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


    @Select("select "+allFields+" from marketing_activity_set where ActivityTitle=#{activityTitle} and OrganizationId=#{organizationId} and id != #{id} and ActivityId = #{activityId} ")
    MarketingActivitySet selectByTitleOrgIdWithActivityIdWhenUpdate(@Param("activityTitle")String activityTitle,@Param("id")Long activitySetid, @Param("organizationId")String organizationId,@Param("activityId")Byte activityId);

    @Update("update marketing_activity_set set CodeTotalNum =CodeTotalNum+#{codeTotalNum} where Id = #{id}")
   void addCodeTotalNum(@Param("codeTotalNum")Long codeNum, @Param("id")Long activitySetid);



    @Update(startScript + " update marketing_activity_set " +
            "<set> "
            + " <if test='activityId !=null and activityId != &apos;&apos; '> ActivityId = #{activityId} ,</if> "
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '> OrganizationId = #{organizationId} ,</if> "
            + " <if test='organizatioIdlName !=null and organizatioIdlName != &apos;&apos; '> OrganizatioIdlName = #{organizatioIdlName} ,</if> "
            + " <if test='activityTitle !=null and activityTitle != &apos;&apos; '> ActivityTitle = #{activityTitle} ,</if> "
            + " <if test='activityStartDate !=null and activityStartDate != &apos;&apos; '> ActivityStartDate = #{activityStartDate} ,</if> "
            + " <if test='activityEndDate !=null and activityEndDate != &apos;&apos; '> ActivityEndDate = #{activityEndDate} ,</if> "
            + " <if test='updateUserId !=null and updateUserId != &apos;&apos; '> UpdateUserId = #{updateUserId} ,</if> "
            + " <if test='updateUserName !=null and updateUserName != &apos;&apos; '> UpdateUserName = #{updateUserName} ,</if> "
            + "  UpdateDate = NOW() , "
            + " <if test='activityStatus !=null and activityStatus != &apos;&apos; '> ActivityStatus = #{activityStatus} ,</if> "
            + " <if test='activityRangeMark !=null and activityRangeMark != &apos;&apos; '> ActivityRangeMark = #{activityRangeMark} ,</if> "
            + " <if test='autoFetch !=null and autoFetch != &apos;&apos; '> autoFetch = #{autoFetch} ,</if> "
            + " <if test='codeTotalNum !=null and codeTotalNum != &apos;&apos; '> CodeTotalNum = #{codeTotalNum} ,</if> "
            + " <if test='activityDesc !=null'> ActivityDesc = #{activityDesc} ,</if> "
		    + " <if test='validCondition !=null and validCondition != &apos;&apos; '> ValidCondition = #{validCondition} ,</if> "
		    + " <if test='sendAudit != null'>SendAudit = #{sendAudit}</if>"
            + " </set> "
            + " where Id = #{id}" +endScript
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
            + " GROUP BY mas.Id DESC"
            + " ORDER BY mas.UpdateDate DESC "
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
            + endScript
    )
    int count(DaoSearchWithOrganizationIdParam searchParams);

    @Update(" UPDATE marketing_activity_set SET ActivityStatus = #{activityStatus}, UpdateUserId = #{userId}, " +
            "UpdateUserName = #{userName}, UpdateDate = NOW() WHERE Id = #{activitySetId} and ActivityId = 3 ")
    void updateSalerActivitySetStatus(@Param("activitySetId") Long activitySetId, @Param("activityStatus") Integer activityStatus, @Param("userId") String userId, @Param("userName") String userName);


    @Delete(" delete from marketing_activity_set where id = #{activitySetId} ")
    int deleteById(@Param("activitySetId") Long activitySetId);
    
    @Select({startScript,
    		"select ",allFields," from marketing_activity_set where OrganizationId = #{organizationId} AND Id in (",
    		"<foreach collection='idList' item='activitySetId' index='index' separator=','>",
			"#{activitySetId}",
			"</foreach>) ",
    		endScript})
    List<MarketingActivitySet> selectMarketingActivitySetByIds(@Param("organizationId")String organizationId, @Param("idList") List<Long> idList);


    @Select({startScript,
            "SELECT a.Id id, b.ActivityName activityName, a.ActivityTitle activityTitle, a.UpdateUserName updateUserName, a.UpdateDate updateDate,",
            "a.ActivityStartDate activityStartDate, a.ActivityEndDate activityEndDate, a.ActivityStatus activityStatus",
            " FROM marketing_activity_set a INNER JOIN marketing_activity b ON a.ActivityId = b.Id WHERE a.ActivityId = 5 ",
            "<if test = 'search != null and search != &apos;&apos;'> AND (",
            " a.ActivityTitle LIKE CONCAT('%', #{search}, '%')",
            " OR a.UpdateUserName LIKE CONCAT('%', #{search}, '%')",
            ") </if>",
            " ORDER BY a.UpdateDate DESC",
            " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber}, #{pageSize}</if>",
            endScript})
    List<PlatformActivityVo> listPlatform(DaoSearchWithUser searchParams);

    @Select({startScript,
            "SELECT COUNT(1) FROM marketing_activity_set WHERE  ",
            " ActivityId = 5 ",
            "<if test = 'search != null and search != &apos;&apos;'> AND (",
            " ActivityTitle LIKE CONCAT('%', #{search}, '%')",
            " OR UpdateUserName LIKE CONCAT('%', #{search}, '%')",
            ") </if>",
            endScript})
    int countPlatform(DaoSearchWithUser searchParams);

    @Select("select "+allFields+" from marketing_activity_set where ActivityTitle=#{activityTitle} and ActivityId = #{activityId}")
    MarketingActivitySet selectByTitlePlatform(@Param("activityTitle")String activityTitle, @Param("activityId") Long activityId);

    @Select("select "+allFields+" from marketing_activity_set where ActivityId=5 and ActivityStatus = 1 AND ActivityStartDate < now() and ActivityEndDate > now()")
    MarketingActivitySet getOnlyPlatformActivity();

}
