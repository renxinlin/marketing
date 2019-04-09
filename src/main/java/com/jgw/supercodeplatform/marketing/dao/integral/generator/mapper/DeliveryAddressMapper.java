package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.DeliveryAddressSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DeliveryAddressMapper {
    @Delete({
        "delete from marketing_delivery_address",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_delivery_address (Id, MemberId, ",
        "MemberName, Name, ",
        "Mobile, Province, ",
        "City, Country, Street, ",
        "ProvinceCode, CityCode, ",
        "CountryCode, StreetCode, ",
        "Detail, Postcode, ",
        "DefaultUsing)",
        "values (#{id,jdbcType=BIGINT}, #{memberId,jdbcType=BIGINT}, ",
        "#{memberName,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{province,jdbcType=VARCHAR}, ",
        "#{city,jdbcType=VARCHAR}, #{country,jdbcType=VARCHAR}, #{street,jdbcType=VARCHAR}, ",
        "#{provinceCode,jdbcType=VARCHAR}, #{cityCode,jdbcType=VARCHAR}, ",
        "#{countryCode,jdbcType=VARCHAR}, #{streetCode,jdbcType=VARCHAR}, ",
        "#{detail,jdbcType=VARCHAR}, #{postcode,jdbcType=VARCHAR}, ",
        "#{defaultUsing,jdbcType=TINYINT})"
    })
    int insert(DeliveryAddress record);

    @InsertProvider(type= DeliveryAddressSqlProvider.class, method="insertSelective")
    int insertSelective(DeliveryAddress record);

    @Select({
        "select",
        "Id, MemberId, MemberName, Name, Mobile, Province, City, Country, Street, ProvinceCode, ",
        "CityCode, CountryCode, StreetCode, Detail, Postcode, DefaultUsing",
        "from marketing_delivery_address",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="MemberId", property="memberId", jdbcType=JdbcType.BIGINT),
        @Result(column="MemberName", property="memberName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="Province", property="province", jdbcType=JdbcType.VARCHAR),
        @Result(column="City", property="city", jdbcType=JdbcType.VARCHAR),
        @Result(column="Country", property="country", jdbcType=JdbcType.VARCHAR),
        @Result(column="Street", property="street", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProvinceCode", property="provinceCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CityCode", property="cityCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CountryCode", property="countryCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="StreetCode", property="streetCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="Detail", property="detail", jdbcType=JdbcType.VARCHAR),
        @Result(column="Postcode", property="postcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="DefaultUsing", property="defaultUsing", jdbcType=JdbcType.TINYINT)
    })
    DeliveryAddress selectByPrimaryKey(Long id);

    @UpdateProvider(type=DeliveryAddressSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DeliveryAddress record);

    @Update({
        "update marketing_delivery_address",
        "set MemberId = #{memberId,jdbcType=BIGINT},",
          "MemberName = #{memberName,jdbcType=VARCHAR},",
          "Name = #{name,jdbcType=VARCHAR},",
          "Mobile = #{mobile,jdbcType=VARCHAR},",
          "Province = #{province,jdbcType=VARCHAR},",
          "City = #{city,jdbcType=VARCHAR},",
          "Country = #{country,jdbcType=VARCHAR},",
          "Street = #{street,jdbcType=VARCHAR},",
          "ProvinceCode = #{provinceCode,jdbcType=VARCHAR},",
          "CityCode = #{cityCode,jdbcType=VARCHAR},",
          "CountryCode = #{countryCode,jdbcType=VARCHAR},",
          "StreetCode = #{streetCode,jdbcType=VARCHAR},",
          "Detail = #{detail,jdbcType=VARCHAR},",
          "Postcode = #{postcode,jdbcType=VARCHAR},",
          "DefaultUsing = #{defaultUsing,jdbcType=TINYINT}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DeliveryAddress record);
}