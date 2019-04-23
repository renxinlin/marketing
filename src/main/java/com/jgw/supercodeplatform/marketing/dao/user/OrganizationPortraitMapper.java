package com.jgw.supercodeplatform.marketing.dao.user;


import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUnitcode;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 会员画像类
 */
@Mapper
public interface OrganizationPortraitMapper extends CommonSql{

    String selectSql = " a.Id as id, a.OrganizationId as organizationId,a.OrganizationFullName as organizationFullName,"
            + " a.UnitCodeId as unitCodeId,a.FieldWeight as fieldWeight ";

    String selectSqlUnitcode = " Id as id ,TypeId as typeId,CodeName as codeName,CodeId as codeId";


    /**
     * 获取组织已选画像
     * @param organizationId
     * @param typeId 14001
     * @return
     */
    @Select(" SELECT "+selectSql+",b.CodeName as codeName,b.TypeId as typeId,b.CodeId as codeId FROM marketing_organization_portrait a left join marketing_unitcode b on a.UnitCodeId = b.Id WHERE a.OrganizationId = #{organizationId} and b.TypeId = #{typeId} ORDER BY a.FieldWeight")
    List<MarketingOrganizationPortraitListParam> getSelectedPortrait(@Param("organizationId")String organizationId,@Param("typeId") Integer typeId);


    /**
     * 添加组织画像关系
     * @param organizationPortrait
     * @return
     */
    @Insert(" INSERT INTO marketing_organization_portrait(OrganizationId,OrganizationFullName,"
            + " UnitCodeId,FieldWeight) "
            + " VALUES(#{organizationId},#{organizationFullName},#{unitCodeId},#{fieldWeight} "
            + ")")
    int addOrgPortrait(MarketingOrganizationPortrait organizationPortrait);


    /**
     * 删除组织画像关系
     * @param organizationPortrait
     * @return
     */
    @Delete(" DELETE FROM marketing_organization_portrait WHERE OrganizationId = #{organizationId}")
    int deleOrgPortrait(@Param("organizationId")String organizationId );



    @Insert(startScript
    		+"insert into marketing_organization_portrait (OrganizationId,OrganizationFullName,UnitCodeId,FieldWeight) values"
    		+ "<foreach collection='list' item='item' index='index' separator=','>" 
    		+       "("
    		+ "        #{item.organizationId},"  
    		+ "        #{item.organizationFullName},"
    		+ "        #{item.unitCodeId},"
    		+ "        #{item.fieldWeight}"
    		+       ")"  
    		+ "</foreach>"
    		+endScript
    		)
	void batchInsert(@Param("list")List<MarketingOrganizationPortrait> mPortraits);

    /**
     * 获取组织没有选择的画像
     *
     * @param organizationId
     * @param organizationId 14001表示画像
     * @return
     */
    @Select(" SELECT "+selectSqlUnitcode+" FROM marketing_unitcode WHERE TypeId = #{typeId} and Id not in (select UnitCodeId from marketing_organization_portrait where OrganizationId = #{organizationId}) ")
	List<MarketingUnitcode> getUnselectedPortrait(@Param("organizationId")String organizationId, @Param("typeId")Integer typeId);

    /**
     * 获取unitcode 中mobile相关信息
     * @return
     */
    @Select(" SELECT "+selectSqlUnitcode+" FROM marketing_unitcode WHERE CodeId = 'Mobile' ")
    MarketingUnitcode getMobilePortrait();

    /**
     *
     * @param organizationId
     * @param typeId 14002
     * @return
     */
    @Select(" SELECT "+selectSqlUnitcode+" FROM marketing_unitcode WHERE TypeId = #{typeId} and  Id not in (select UnitCodeId from marketing_organization_portrait where OrganizationId = #{organizationId}) ")
    List<MarketingOrganizationPortraitListParam> getUnSelectedLabel(String organizationId,@Param("typeId")Integer typeId);

    /**
     *
     * @param organizationId
     * @param typeId 14002
     * @return
     */
    @Select(" SELECT "+selectSql+",b.CodeName as codeName,b.TypeId as typeId,b.CodeId as codeId FROM marketing_organization_portrait a left join marketing_unitcode b on a.UnitCodeId = b.Id WHERE a.OrganizationId = #{organizationId} and b.TypeId = #{typeId} ORDER BY a.FieldWeight")
    List<MarketingOrganizationPortraitListParam> getSelectedLabel(String organizationId,@Param("typeId")Integer typeId);


    @Select(" SELECT "+selectSql+",b.CodeName as codeName,b.TypeId as typeId,b.CodeId as codeId FROM marketing_organization_portrait a left join marketing_unitcode b on a.UnitCodeId = b.Id WHERE a.OrganizationId = #{organizationId}  ORDER BY a.FieldWeight")
    List<MarketingOrganizationPortraitListParam> getSelectedPortraitAndLabel(@Param("organizationId")String organizationId);

}
