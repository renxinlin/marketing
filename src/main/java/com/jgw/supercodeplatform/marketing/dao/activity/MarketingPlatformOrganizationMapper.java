package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.pojo.platform.MarketingPlatformOrganization;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketingPlatformOrganizationMapper {

    @Insert({"<script>",
            "INSERT INTO marketing_platform_organization (OrganizationId, OrganizationFullName, ActivitySetId)",
            "VALUES <foreach collection='platformOrganizationList' item='platformOrganization' separator=','>",
            "(#{platformOrganization.organizationId}, #{platformOrganization.organizationFullName}, #{platformOrganization.activitySetId})",
            "</foreach>",
    "</script>"})
    int insertList(@Param("platformOrganizationList") List<MarketingPlatformOrganization> platformOrganizationList);

    @Delete("DELETE FROM marketing_platform_organization WHERE ActivitySetId = #{activitySetId}")
    int delteByActivitySetId(@Param("activitySetId") Long activitySetId);

    @Select("SELECT * FROM marketing_platform_organization WHERE ActivitySetId = #{activitySetId}")
    List<MarketingPlatformOrganization> selectByActivitySetId(@Param("activitySetId") Long activitySetId);

    @Select("SELECT * FROM marketing_platform_organization WHERE ActivitySetId = #{activitySetId} AND OrganizationId = #{organizationId}")
    MarketingPlatformOrganization selectByActivitySetIdAndOrganizationId(@Param("activitySetId") Long activitySetId, @Param("organizationId") String organizationId);

    @Select("SELECT * FROM marketing_platform_organization WHERE OrganizationId = #{organizationId}")
    List<MarketingPlatformOrganization> selectByOrgId(@Param("organizationId") String organizationId);


}
