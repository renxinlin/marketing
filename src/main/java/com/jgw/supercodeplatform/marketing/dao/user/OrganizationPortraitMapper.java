package com.jgw.supercodeplatform.marketing.dao.user;


import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUnitcode;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 会员画像类
 */
@Mapper
public interface OrganizationPortraitMapper {

    String selectSql = " a.Id as id, a.OrganizationId as organizationId,a.OrganizationFullName as organizationFullName,"
            + " a.PortraitCode as portraitCode,a.PortraitName as portraitName,a.FieldWeight as fieldWeight ";

    String selectSqlUnitcode = " Id as id ,TypeId as typeId,CodeName as codeName,CodeId as codeId";


    /**
     * 获取组织已选画像
     * @param organizationId
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_organization_portrait a WHERE a.OrganizationId = #{organizationId} ORDER BY a.FieldWeight")
    List<MarketingOrganizationPortrait> getSelectedPortrait(@Param("organizationId")String organizationId);


    /**
     * 添加组织画像关系
     * @param organizationPortrait
     * @return
     */
    @Insert(" INSERT INTO marketing_organization_portrait(OrganizationId,OrganizationFullName,"
            + " PortraitCode,PortraitName,FieldWeight) "
            + " VALUES(#{organizationId},#{organizationFullName},#{portraitCode},#{portraitName},#{fieldWeight} "
            + ")")
    int addOrgPortrait(MarketingOrganizationPortrait organizationPortrait);


    /**
     * 删除组织画像关系
     * @param organizationPortrait
     * @return
     */
    @Delete(" DELETE FROM marketing_organization_portrait WHERE OrganizationId = #{organizationId} AND PortraitCode = #{portraitCode}")
    int deleOrgPortrait(MarketingOrganizationPortrait organizationPortrait);


    /**
     * 获取所有的画像
     * @return
     */
    @Select("SELECT "+selectSqlUnitcode+" FROM marketing_unitcode WHERE TypeId = '14001' OR TypeId = '14002' ")
    List<MarketingUnitcode> getAllUnitcode();

    /**
     * 条件查询所有的画像
     * @param map
     * @return
     */
    @Select(" <script>"
            + " SELECT " + selectSqlUnitcode
            + " FROM marketing_unitcode  "
            + " <where>"
            + " <if test='typeId !=null and typeId != &apos;&apos; '> TypeId = #{typeId} </if> "
            + " <if test='codeName !=null and codeName != &apos;&apos; '> AND CodeName = #{codeName}</if> "
            + " <if test='codeId !=null and codeId != &apos;&apos; '> AND CodeId = #{codeId}</if> "
            + " <if test='codeIdList !=null and codeIdList.size() !=0 '>  AND CodeId IN "
            + " <foreach item='codeId' index='index' collection='codeIdList' open='(' separator=',' close=')'> #{codeId} </foreach> "
            + " </if> "
            + " </where>"
            + " </script>")
    List<MarketingUnitcode> getPortraitsList(Map<String, Object> map);


    /**
     * 获取组织已选画像数量
     * @param organizationId
     * @return
     */
    @Select(" SELECT COUNT(1)  FROM marketing_organization_portrait a WHERE a.OrganizationId = #{organizationId} ")
    int getSelectedPortraitCount(@Param("organizationId")String organizationId);


    /**
     * 修改组织画像
     * @param map
     * @return
     */
    @Update(" <script>"
            + " UPDATE marketing_organization_portrait "
            + " <set>"
            + " <if test='portraitName !=null and portraitName != &apos;&apos; '> PortraitName = #{portraitName} ,</if> "
            + " <if test='fieldWeight !=null and fieldWeight != &apos;&apos; '> FieldWeight = #{fieldWeight} ,</if> "
            + " </set>"
            + " <where> "
            + " <if test='id !=null and id != &apos;&apos; '> and Id = #{id} </if>"
            + " <if test='portraitCode !=null and portraitCode != &apos;&apos; '> and PortraitCode = #{portraitCode} </if> "
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"
            + " </where>"
            + " </script>")
    int updatePortraits(Map<String,Object> map);


    /**
     * 获取单个组织画像
     * @param organizationPortrait
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_organization_portrait a WHERE PortraitCode = #{portraitCode} AND OrganizationId = #{organizationId} ")
    MarketingOrganizationPortrait getPortraitByPortraitCode(MarketingOrganizationPortrait organizationPortrait);

    @Select(" SELECT "+selectSqlUnitcode+" FROM marketing_unitcode WHERE CodeId = #{codeId} ")
    MarketingUnitcode getUnitcodeByCode(@Param("codeId")String codeId);

}
