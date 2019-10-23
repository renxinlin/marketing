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
        "State, DeviceType,HaveIntegral)",
        "values (#{id,jdbcType=BIGINT}, #{wxName,jdbcType=VARCHAR}, ",
        "#{openid,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR}, ",
        "#{sex,jdbcType=VARCHAR}, #{birthday,jdbcType=TIMESTAMP}, ",
        "#{provinceCode,jdbcType=VARCHAR}, #{countyCode,jdbcType=VARCHAR}, ",
        "#{cityCode,jdbcType=VARCHAR}, #{provinceName,jdbcType=VARCHAR}, ",
        "#{countyName,jdbcType=VARCHAR}, #{cityName,jdbcType=VARCHAR}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{customerName,jdbcType=VARCHAR}, ",
        "#{customerId,jdbcType=VARCHAR}, #{pCCcode,jdbcType=VARCHAR}, ",
        "#{wechatHeadImgUrl,jdbcType=VARCHAR}, #{memberType,jdbcType=TINYINT}, ",
            "#{state,jdbcType=TINYINT}, #{deviceType,jdbcType=TINYINT}, #{haveIntegral,jdbcType=INTEGER})"
    })
    int insert(MarketingUser record);

    @InsertProvider(type= MarketingUserSqlProvider.class, method="insertSelective")
    int insertSelective(MarketingUser record);

    @Select({
        "select",
        "Id, WxName, Openid, Mobile, UserId, UserName, Sex, Birthday, ProvinceCode, CountyCode, ",
        "CityCode, ProvinceName, CountyName, CityName, OrganizationId, CreateDate, UpdateDate, ",
        "CustomerName, CustomerId, PCCcode, WechatHeadImgUrl, MemberType, State, DeviceType,HaveIntegral",
        "from marketing_user",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
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
        @Result(column="PCCcode", property="pCCcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="MemberType", property="memberType", jdbcType=JdbcType.TINYINT),
        @Result(column="State", property="state", jdbcType=JdbcType.TINYINT),
        @Result(column="DeviceType", property="deviceType", jdbcType=JdbcType.TINYINT),
        @Result(column="HaveIntegral", property="haveIntegral", jdbcType=JdbcType.INTEGER)
    })
    MarketingUser selectByPrimaryKey(Long id);

    @UpdateProvider(type=MarketingUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MarketingUser record);

    /**
     * pcccode不为空修改行政字段
     * @param record
     * @return
     */
    @Update(" <script>"
            + " UPDATE marketing_user "
            + " <set>"
            + " <if test='wxName !=null and wxName != &apos;&apos; '> WxName = #{wxName} ,</if> "
            + " <if test='openid !=null and openid != &apos;&apos; '> Openid = #{openid} ,</if> "
            + " <if test='mobile !=null and mobile != &apos;&apos; '> Mobile = #{mobile} ,</if> "
            + " <if test='userId !=null'> UserId = #{userId} ,</if> "
            + " <if test='sex !=null'> Sex = #{sex} ,</if> "
            + " <if test='birthday !=null and birthday != &apos;&apos; '> Birthday = #{birthday} ,</if> "
            + " <if test='pCCcode !=null and pCCcode != &apos;&apos; '> PCCcode = #{pCCcode} ,</if> "
            + " <if test='countyCode !=null'> CountyCode = #{countyCode} ,</if> "
            + " <if test='cityCode !=null'> CityCode = #{cityCode} ,</if> "
            + " <if test='provinceCode !=null'> ProvinceCode = #{provinceCode} ,</if> "
            + " <if test='countyName !=null'> CountyName = #{countyName} ,</if> "
            + " <if test='cityName !=null'> CityName = #{cityName} ,</if> "
            + " <if test='provinceName !=null'> ProvinceName = #{provinceName} ,</if> "
            + " <if test='customerName !=null and customerName != &apos;&apos; '> CustomerName = #{customerName} ,</if> "
            + " <if test='customerId !=null and customerId != &apos;&apos; '> CustomerId = #{customerId} ,</if> "
            + " <if test='memberType !=null  '> MemberType = #{memberType} ,</if> "
            + " <if test='state !=null  '> State = #{state} ,</if> "
            + " <if test='deviceType !=null '> DeviceType = #{deviceType} ,</if> "
            + " <if test='haveIntegral !=null '> HaveIntegral = #{haveIntegral} ,</if> "
            + " UpdateDate = NOW() "
            + " </set>"
            + " <where> "
            + "  Id = #{id} "
            + " </where>"
            + " </script>")
    int updateByPrimaryKeySelectiveWithBiz(MarketingUser record);

}