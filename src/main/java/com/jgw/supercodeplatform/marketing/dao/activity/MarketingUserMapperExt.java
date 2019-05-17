package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MarketingUserMapperExt extends MarketingUserMapper {

    String selectSql = " a.Id as id, a.WxName as wxName,a.Openid as openid,a.Mobile as mobile, "
            + " a.UserId as userId,a.UserName as userName, "
            + " a.Sex as sex,DATE_FORMAT(a.Birthday ,'%Y-%m-%d') as birthday, "
            + " a.ProvinceCode, a.CountyCode,a.CityCode, a.ProvinceName,a.CountyName, a.CityName, "
            + " a.OrganizationId as organizationId,"
            + " DATE_FORMAT(a.CreateDate,'%Y-%m-%d') as createDate,DATE_FORMAT(a.UpdateDate,'%Y-%m-%d') as updateDate, "
            + " a.CustomerName as customerName,a.CustomerId as customerId,"
            + " a.PCCcode as pCCcode, a.WechatHeadImgUrl as wechatHeadImgUrl, "
            + " a.MemberType memberType, "
            + " a.State as state, a.DeviceType  ";


    /**
     * 条件查询会员
     * @return
     */
    @Select(" <script>"
            + " SELECT "+selectSql+" FROM marketing_user a  "
            + " <where>"
            + "  OrganizationId = #{ organizationId }  "
            + " <if test='mobile != null and mobile != &apos;&apos;'> AND Mobile like &apos;%${mobile}%&apos; </if>"
            + " <if test='wxName != null and wxName != &apos;&apos;'> AND WxName like &apos;%${wxName}%&apos; </if>"
            + " <if test='openid != null and openid != &apos;&apos;'> AND Openid like &apos;%${openid}%&apos; </if>"
            + " <if test='userName != null and userName != &apos;&apos;'> AND UserName like &apos;%${userName}%&apos; </if>"
            + " <if test='birthday != null and birthday != &apos;&apos;'> AND Birthday like &apos;%${birthday}%&apos; </if>"
            + " <if test='provinceName != null and provinceName != &apos;&apos;'> AND ProvinceName like &apos;%${provinceName}%&apos; </if>"
            + " <if test='countyName != null and countyName != &apos;&apos;'> AND CountyName like &apos;%${countyName}%&apos; </if>"
            + " <if test='cityName != null and cityName != &apos;&apos;'> AND CityName like &apos;%${cityName}%&apos; </if>"
            + " <if test='customerName != null and customerName != &apos;&apos;'> AND CustomerName like &apos;%${customerName}%&apos; </if>"
            + " <if test='state != null and state != &apos;&apos;'> AND State like &apos;%${state}%&apos; </if>"
            + " <if test='search !=null and search != &apos;&apos;'>"
            + " AND ("
            + " Mobile LIKE binary CONCAT('%',#{search},'%')  "
            + " OR WxName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Openid LIKE binary CONCAT('%',#{search},'%') "
            + " OR UserName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Sex LIKE binary CONCAT('%',#{search},'%') "
            + " OR Birthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR CreateDate LIKE binary CONCAT('%',#{search},'%') "
            + " OR ProvinceName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CountyName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CityName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CustomerName LIKE binary CONCAT('%',#{search},'%') "
            + " OR State LIKE binary CONCAT('%',#{search},'%') "
            + ")"
            + " </if>"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            + " </where>"
            + " </script>")
    List<MarketingUser> list(MarketingMembersListParam searchParams);


    /**
     * 条件查询会员数量
     * @return
     */
    @Select(" <script>"
            + " SELECT "+selectSql+" FROM marketing_user a  "
            + " <where>"
            + "  OrganizationId = #{ organizationId }  "
            + " <if test='mobile != null and mobile != &apos;&apos;'> AND Mobile like &apos;%${mobile}%&apos; </if>"
            + " <if test='wxName != null and wxName != &apos;&apos;'> AND WxName like &apos;%${wxName}%&apos; </if>"
            + " <if test='openid != null and openid != &apos;&apos;'> AND Openid like &apos;%${openid}%&apos; </if>"
            + " <if test='userName != null and userName != &apos;&apos;'> AND UserName like &apos;%${userName}%&apos; </if>"
            + " <if test='birthday != null and birthday != &apos;&apos;'> AND Birthday like &apos;%${birthday}%&apos; </if>"
            + " <if test='provinceName != null and provinceName != &apos;&apos;'> AND ProvinceName like &apos;%${provinceName}%&apos; </if>"
            + " <if test='countyName != null and countyName != &apos;&apos;'> AND CountyName like &apos;%${countyName}%&apos; </if>"
            + " <if test='cityName != null and cityName != &apos;&apos;'> AND CityName like &apos;%${cityName}%&apos; </if>"
            + " <if test='customerName != null and customerName != &apos;&apos;'> AND CustomerName like &apos;%${customerName}%&apos; </if>"
            + " <if test='state != null and state != &apos;&apos;'> AND State like &apos;%${state}%&apos; </if>"
            + " <if test='search !=null and search != &apos;&apos;'>"
            + " AND ("
            + " Mobile LIKE binary CONCAT('%',#{search},'%')  "
            + " OR WxName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Openid LIKE binary CONCAT('%',#{search},'%') "
            + " OR UserName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Sex LIKE binary CONCAT('%',#{search},'%') "
            + " OR Birthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR CreateDate LIKE binary CONCAT('%',#{search},'%') "
            + " OR ProvinceName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CountyName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CityName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CustomerName LIKE binary CONCAT('%',#{search},'%') "
            + " OR State LIKE binary CONCAT('%',#{search},'%') "
            + ")"
            + " </if>"
            + " </where>"
            + " </script>")
    Integer count(MarketingMembersListParam searchParams);

}
