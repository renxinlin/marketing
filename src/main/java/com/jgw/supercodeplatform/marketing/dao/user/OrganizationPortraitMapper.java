package com.jgw.supercodeplatform.marketing.dao.user;


import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 会员画像类
 */
@Mapper
public interface OrganizationPortraitMapper {

    String selectSql = " a.OrganizationId as organizationId,a.OrganizationFullName as organizationFullName,"
            + " a.PortraitCode as portraitCode,a.PortraitName as portraitName,";


    /**
     * 获取组织已选画像
     * @param organizationId
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_organization_portrait a WHERE a.OrganizationId = #{organizationId}")
    List<MarketingOrganizationPortrait> getSelectedPortrait(@Param("organizationId")String organizationId);


    /**
     * 添加组织画像关系
     * @param organizationPortrait
     * @return
     */
    @Insert(" INSERT INTO marketing_organization_portrait(OrganizationId,OrganizationFullName,"
            + " PortraitCode,PortraitName "
            + " VALUES(#{organizationId},#{organizationFullName},#{portraitCode},#{portraitName} "
            + ")")
    int addOrgPortrait(MarketingOrganizationPortrait organizationPortrait);


    /**
     * 删除组织画像关系
     * @param organizationPortrait
     * @return
     */
    @Delete(" DELETE FROM marketing_organization_portrait WHERE OrganizationId = #{organizationId} AND PortraitCode = #{portraitCode}")
    int deleOrgPortrait(MarketingOrganizationPortrait organizationPortrait);
}
