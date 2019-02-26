package com.jgw.supercodeplatform.marketing.dao.user;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 会员类
 */
@Mapper
public interface MarketingMembersMapper extends CommonSql {
    String selectSql = " a.WxName as wxName,a.Openid as openid,a.Mobile as mobile,"
            + " a.UserId as userId,a.UserName as userName,"
            + " a.Sex as sex,a.Birthday as birthday,a.ProvinceCode as provinceCode,"
            + " a.CountyCode as countyCode,a.CityCode as cityCode,a.ProvinceName as provinceName,"
            + " a.CountyName as countyName,a.CityName as cityName,"
            + " DATE_FORMAT(a.RegistDate,'%Y-%m-%d') as registDate,"
            + " a.State as state,a.OrganizationId as organizationId,a.OrganizationFullName as organizationFullName,"
            + " a.NewRegisterFlag as newRegisterFlag ,a.Stores as stores,a.StoresType as storesType,"
            + " a.BabyBirthday as babyBirthday ";


    String whereSelectMem =
            "<where>" +
                    "<choose>" +
                    //高级搜索
                    "<when test='search = null and (params.mobile != null or params.wxName != null or params.openid != null or params.userName != null or params.sex != null or params.birthday != null " +
                    " or params.provinceName != null or params.countyName != null or params.cityName != null or params.stores != null or params.newRegisterFlag != null or params.registDate != null or params.babyBirthday != null or params.state != null ) '>" +
                    "<if test='params.id != null and params.id != &apos;&apos;'> AND Id like &apos;%${params.id}%&apos; </if>" +
                    "<if test='params.mobile != null and params.mobile != &apos;&apos;'> AND Mobile like &apos;%${params.mobile}%&apos; </if>" +
                    "<if test='params.wxName != null and params.wxName != &apos;&apos;'> AND WxName like &apos;%${params.wxName}%&apos; </if>" +
                    "<if test='params.openid != null and params.openid != &apos;&apos;'> AND Openid like &apos;%${params.openid}%&apos; </if>" +
                    "<if test='params.userName != null and params.userName != &apos;&apos;'> AND UserName like &apos;%${params.userName}%&apos; </if>" +
                    "<if test='params.sex != null and params.sex != &apos;&apos;'> AND Sex like &apos;%${params.sex}%&apos; </if>" +
                    "<if test='params.birthday != null and params.birthday != &apos;&apos;'> AND Birthday like &apos;%${params.birthday}%&apos; </if>" +
                    "<if test='params.provinceName != null and params.provinceName != &apos;&apos;'> AND ProvinceName like &apos;%${params.provinceName}%&apos; </if>" +
                    "<if test='params.countyName != null and params.countyName != &apos;&apos;'> AND CountyName like &apos;%${params.countyName}%&apos; </if>" +
                    "<if test='params.cityName != null and params.cityName != &apos;&apos;'> AND CityName like &apos;%${params.cityName}%&apos; </if>" +
                    "<if test='params.stores != null and params.stores != &apos;&apos;'> AND Stores like &apos;%${params.stores}%&apos; </if>" +
                    "<if test='params.newRegisterFlag != null and params.newRegisterFlag != &apos;&apos;'> AND NewRegisterFlag like &apos;%${params.newRegisterFlag}%&apos; </if>" +
                    "<if test='params.registDate != null and params.registDate != &apos;&apos;'> AND RegistDate like &apos;%${params.registDate}%&apos; </if>" +
                    "<if test='params.babyBirthday != null and params.babyBirthday != &apos;&apos;'> AND BabyBirthday like &apos;%${params.babyBirthday}%&apos; </if>" +
                    "<if test='params.state != null and params.state != &apos;&apos;'> AND State like &apos;%${params.state}%&apos; </if>" +
                    "</when>" +
                    //普通搜索
                    "<when test='params.mobile = null and params.wxName = null and params.openid = null and params.userName = null and params.sex = null and params.birthday = null " +
                    " and params.provinceName = null and params.countyName = null and params.cityName = null and params.stores = null and params.newRegisterFlag = null and params.registDate = null and params.babyBirthday = null and params.state = null '>" +
                    "<if test='search !=null and search != &apos;&apos;'>" +
                    " AND (" +
                    " Mobile LIKE CONCAT('%',#{search},'%')  " +
                    " OR WxName LIKE CONCAT('%',#{search},'%') " +
                    " OR Openid LIKE CONCAT('%',#{search},'%') " +
                    " OR UserName LIKE CONCAT('%',#{search},'%') " +
                    " OR Sex LIKE CONCAT('%',#{search},'%') " +
                    " OR Birthday LIKE CONCAT('%',#{search},'%') " +
                    " OR ProvinceName LIKE CONCAT('%',#{search},'%') " +
                    " OR CountyName LIKE CONCAT('%',#{search},'%') " +
                    " OR CityName LIKE CONCAT('%',#{search},'%') " +
                    " OR Stores LIKE CONCAT('%',#{search},'%') " +
                    " OR NewRegisterFlag LIKE CONCAT('%',#{search},'%') " +
                    " OR RegistDate LIKE CONCAT('%',#{search},'%') " +
                    " OR BabyBirthday LIKE CONCAT('%',#{search},'%') " +
                    " OR State LIKE CONCAT('%',#{search},'%') " +
                    ")" +
                    "</if>" +
                    "</when>" +
                    //其他，则为默认所有，不进行筛选
                    "<otherwise>" +
                    "</otherwise>" +
                    "</choose>" +
                    "</where>";


    /**
     * 会员注册
     * @param members
     * @return
     */
    @Insert(" INSERT INTO marketing_members(WxName,Openid,Mobile,UserId,UserName,"
            + " Sex,Birthday,ProvinceCode,CountyCode,CityCode,ProvinceName,CountyName,"
            + " CityName,RegistDate,State,OrganizationId,OrganizationFullName,NewRegisterFlag,Stores,StoresType,BabyBirthday)"
            + " VALUES(#{wxName},#{openid},#{mobile},#{userId},#{userName},#{sex},#{birthday},#{provinceCode},#{countyCode},#{cityCode},"
            + " #{provinceName},#{countyName},#{cityName},#{registDate},#{state},#{organizationId},#{organizationFullName},"
            + " #{newRegisterFlag},#{stores},#{storesType},#{babyBirthday} )")
    int addMembers(Map<String,Object> map);


    /**
     * 条件查询会员
     * @return
     */
    @Select(" <script>"
            + " SELECT "+selectSql+" FROM marketing_members a "
            + whereSelectMem
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            + " </script>")
    List<MarketingMembers> getAllMarketingMembersLikeParams(Map<String,Object> map);


    /**
     * 修改会员信息
     * @param members
     * @return
     */
    @Update(" <script>"
            + " UPDATE marketing_members "
            + " <set>"
            + " <if test='userName !=null and userName != &apos;&apos; '> UserName = #{userName} ,</if> "
            + " <if test='sex !=null and sex != &apos;&apos; '> Sex = #{sex} ,</if> "
            + " <if test='birthday !=null and birthday != &apos;&apos; '> Birthday = #{birthday} ,</if> "
            + " <if test='provinceCode !=null and provinceCode != &apos;&apos; '> ProvinceCode = #{provinceCode} ,</if> "
            + " <if test='countyCode !=null and countyCode != &apos;&apos; '> CountyCode = #{countyCode} ,</if> "
            + " <if test='cityCode !=null and cityCode != &apos;&apos; '> CityCode = #{cityCode} ,</if> "
            + " <if test='provinceName !=null and provinceName != &apos;&apos; '> ProvinceName = #{provinceName} ,</if> "
            + " <if test='countyName !=null and countyName != &apos;&apos; '> CountyName = #{countyName} ,</if> "
            + " <if test='cityName !=null and cityName != &apos;&apos; '> CityName = #{cityName} ,</if> "
            + " <if test='newRegisterFlag !=null and newRegisterFlag != &apos;&apos; '> NewRegisterFlag = #{newRegisterFlag} ,</if> "
            + " <if test='state !=null and state != &apos;&apos; '> State = #{state} ,</if> "
            + " <if test='stores !=null and stores != &apos;&apos; '> Stores = #{stores} ,</if> "
            + " <if test='storesType !=null and storesType != &apos;&apos; '> StoresType = #{storesType} ,</if> "
            + " <if test='babyBirthday !=null and babyBirthday != &apos;&apos; '> BabyBirthday = #{babyBirthday} ,</if> "
            + " </set>"
            + " <where> "
            + " <if test='id !=null and id != &apos;&apos; '> and Id = #{id} </if>"
            + " <if test='userId !=null and userId != &apos;&apos; '> and UserId = #{userId} </if> "
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"
            + " </where>"
            + " </script>")
    int updateMembers(Map<String,Object> map);


    /**
     * 根据id获取单个会员信息
     * @param id
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE Id = #{id} AND OrganizationId = #{organizationId} ")
    MarketingMembers getMemberById(@Param("id")int id,@Param("organizationId")String  organizationId);







}
