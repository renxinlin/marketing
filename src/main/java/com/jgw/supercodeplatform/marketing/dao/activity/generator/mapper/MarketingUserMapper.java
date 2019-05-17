package com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.activity.generator.provider.MarketingUserSqlProvider;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface MarketingUserMapper {
    @Delete({
        "delete from marketing_user",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_user (Id, WxName, ",
        "Openid, Mobile, ",
        "UserId, UserName, ",
        "Sex, Birthday, ",
        "ProvinceCode, CountyCode, ",
        "CityCode, ProvinceName, ",
        "CountyName, CityName, ",
        "OrganizationId, CreateDate, ",
        "UpdateDate, CustomerName, ",
        "CustomerId, PCCcode, ",
        "WechatHeadImgUrl, MemberType, ",
        "State, DeviceType)",
        "values (#{id,jdbcType=BIGINT}, #{wxName,jdbcType=VARCHAR}, ",
        "#{openid,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR}, ",
        "#{sex,jdbcType=VARCHAR}, #{birthday,jdbcType=TIMESTAMP}, ",
        "#{provinceCode,jdbcType=VARCHAR}, #{countyCode,jdbcType=VARCHAR}, ",
        "#{cityCode,jdbcType=VARCHAR}, #{provinceName,jdbcType=VARCHAR}, ",
        "#{countyName,jdbcType=VARCHAR}, #{cityName,jdbcType=VARCHAR}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{customerName,jdbcType=VARCHAR}, ",
        "#{customerId,jdbcType=VARCHAR}, #{PCCcode,jdbcType=VARCHAR}, ",
        "#{wechatHeadImgUrl,jdbcType=VARCHAR}, #{memberType,jdbcType=TINYINT}, ",
        "#{state,jdbcType=TINYINT}, #{deviceType,jdbcType=TINYINT})"
    })
    int insert(MarketingUser record);

    @InsertProvider(type= MarketingUserSqlProvider.class, method="insertSelective")
    int insertSelective(MarketingUser record);

    @Select({
        "select",
        "Id, WxName, Openid, Mobile, UserId, UserName, Sex, Birthday, ProvinceCode, CountyCode, ",
        "CityCode, ProvinceName, CountyName, CityName, OrganizationId, CreateDate, UpdateDate, ",
        "CustomerName, CustomerId, PCCcode, WechatHeadImgUrl, MemberType, State, DeviceType",
        "from marketing_user",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="WxName", property="wxName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Openid", property="openid", jdbcType=JdbcType.VARCHAR),
        @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="UserId", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="UserName", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Sex", property="sex", jdbcType=JdbcType.VARCHAR),
        @Result(column="Birthday", property="birthday", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ProvinceCode", property="provinceCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CountyCode", property="countyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CityCode", property="cityCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProvinceName", property="provinceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CountyName", property="countyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CityName", property="cityName", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateDate", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CustomerName", property="customerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CustomerId", property="customerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PCCcode", property="PCCcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="WechatHeadImgUrl", property="wechatHeadImgUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="MemberType", property="memberType", jdbcType=JdbcType.TINYINT),
        @Result(column="State", property="state", jdbcType=JdbcType.TINYINT),
        @Result(column="DeviceType", property="deviceType", jdbcType=JdbcType.TINYINT)
    })
    MarketingUser selectByPrimaryKey(Long id);

    @UpdateProvider(type=MarketingUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MarketingUser record);

    @Update({
        "update marketing_user",
        "set WxName = #{wxName,jdbcType=VARCHAR},",
          "Openid = #{openid,jdbcType=VARCHAR},",
          "Mobile = #{mobile,jdbcType=VARCHAR},",
          "UserId = #{userId,jdbcType=VARCHAR},",
          "UserName = #{userName,jdbcType=VARCHAR},",
          "Sex = #{sex,jdbcType=VARCHAR},",
          "Birthday = #{birthday,jdbcType=TIMESTAMP},",
          "ProvinceCode = #{provinceCode,jdbcType=VARCHAR},",
          "CountyCode = #{countyCode,jdbcType=VARCHAR},",
          "CityCode = #{cityCode,jdbcType=VARCHAR},",
          "ProvinceName = #{provinceName,jdbcType=VARCHAR},",
          "CountyName = #{countyName,jdbcType=VARCHAR},",
          "CityName = #{cityName,jdbcType=VARCHAR},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "UpdateDate = #{updateDate,jdbcType=TIMESTAMP},",
          "CustomerName = #{customerName,jdbcType=VARCHAR},",
          "CustomerId = #{customerId,jdbcType=VARCHAR},",
          "PCCcode = #{PCCcode,jdbcType=VARCHAR},",
          "WechatHeadImgUrl = #{wechatHeadImgUrl,jdbcType=VARCHAR},",
          "MemberType = #{memberType,jdbcType=TINYINT},",
          "State = #{state,jdbcType=TINYINT},",
          "DeviceType = #{deviceType,jdbcType=TINYINT}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MarketingUser record);

}