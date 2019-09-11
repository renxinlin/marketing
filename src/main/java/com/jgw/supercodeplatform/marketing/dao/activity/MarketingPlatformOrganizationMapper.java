package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingPlatformOrganization;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MarketingPlatformOrganizationMapper {

    @Insert({"<script>",
            "INSERT INTO marketing_platform_organization (OrganizationId, OrganizationFullName, ActivitySetId)",
            "VALUES <foreach collection='platformOrganizationList' item='platformOrganization' separator=','>",
            "(#{platformOrganization.organizationId}, #{platformOrganization.organizationFullName}, #{platformOrganization.activitySetId})",
            "</foreach>",
    "<script>"})
    int insertList(@Param("platformOrganizationList") List<MarketingPlatformOrganization> platformOrganizationList);

}
