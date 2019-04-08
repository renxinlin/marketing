package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.DeliveryAddressSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
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
        "Detail, Postcode)",
        "values (#{id,jdbcType=BIGINT}, #{memberid,jdbcType=INTEGER}, ",
        "#{membername,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{province,jdbcType=VARCHAR}, ",
        "#{city,jdbcType=VARCHAR}, #{country,jdbcType=VARCHAR}, #{street,jdbcType=VARCHAR}, ",
        "#{provincecode,jdbcType=VARCHAR}, #{citycode,jdbcType=VARCHAR}, ",
        "#{countrycode,jdbcType=VARCHAR}, #{streetcode,jdbcType=VARCHAR}, ",
        "#{detail,jdbcType=VARCHAR}, #{postcode,jdbcType=VARCHAR})"
    })
    int insert(DeliveryAddress record);

    @InsertProvider(type= DeliveryAddressSqlProvider.class, method="insertSelective")
    int insertSelective(DeliveryAddress record);

    @Select({
        "select",
        "Id, MemberId, MemberName, Name, Mobile, Province, City, Country, Street, ProvinceCode, ",
        "CityCode, CountryCode, StreetCode, Detail, Postcode",
        "from marketing_delivery_address",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="MemberId", property="memberid", jdbcType=JdbcType.INTEGER),
        @Result(column="MemberName", property="membername", jdbcType=JdbcType.VARCHAR),
        @Result(column="Name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="Province", property="province", jdbcType=JdbcType.VARCHAR),
        @Result(column="City", property="city", jdbcType=JdbcType.VARCHAR),
        @Result(column="Country", property="country", jdbcType=JdbcType.VARCHAR),
        @Result(column="Street", property="street", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProvinceCode", property="provincecode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CityCode", property="citycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CountryCode", property="countrycode", jdbcType=JdbcType.VARCHAR),
        @Result(column="StreetCode", property="streetcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="Detail", property="detail", jdbcType=JdbcType.VARCHAR),
        @Result(column="Postcode", property="postcode", jdbcType=JdbcType.VARCHAR)
    })
    DeliveryAddress selectByPrimaryKey(Long id);

    @UpdateProvider(type=DeliveryAddressSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DeliveryAddress record);

    @Update({
        "update marketing_delivery_address",
        "set MemberId = #{memberid,jdbcType=INTEGER},",
          "MemberName = #{membername,jdbcType=VARCHAR},",
          "Name = #{name,jdbcType=VARCHAR},",
          "Mobile = #{mobile,jdbcType=VARCHAR},",
          "Province = #{province,jdbcType=VARCHAR},",
          "City = #{city,jdbcType=VARCHAR},",
          "Country = #{country,jdbcType=VARCHAR},",
          "Street = #{street,jdbcType=VARCHAR},",
          "ProvinceCode = #{provincecode,jdbcType=VARCHAR},",
          "CityCode = #{citycode,jdbcType=VARCHAR},",
          "CountryCode = #{countrycode,jdbcType=VARCHAR},",
          "StreetCode = #{streetcode,jdbcType=VARCHAR},",
          "Detail = #{detail,jdbcType=VARCHAR},",
          "Postcode = #{postcode,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DeliveryAddress record);
}