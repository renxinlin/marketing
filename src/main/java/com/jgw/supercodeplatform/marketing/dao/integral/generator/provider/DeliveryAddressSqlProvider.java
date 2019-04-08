package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;

public class DeliveryAddressSqlProvider {

    public String insertSelective(DeliveryAddress record) {
        BEGIN();
        INSERT_INTO("marketing_delivery_address");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getMemberId() != null) {
            VALUES("MemberId", "#{memberId,jdbcType=INTEGER}");
        }
        
        if (record.getMemberName() != null) {
            VALUES("MemberName", "#{memberName,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            VALUES("Name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            VALUES("Mobile", "#{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getProvince() != null) {
            VALUES("Province", "#{province,jdbcType=VARCHAR}");
        }
        
        if (record.getCity() != null) {
            VALUES("City", "#{city,jdbcType=VARCHAR}");
        }
        
        if (record.getCountry() != null) {
            VALUES("Country", "#{country,jdbcType=VARCHAR}");
        }
        
        if (record.getStreet() != null) {
            VALUES("Street", "#{street,jdbcType=VARCHAR}");
        }
        
        if (record.getProvinceCode() != null) {
            VALUES("ProvinceCode", "#{provinceCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCityCode() != null) {
            VALUES("CityCode", "#{cityCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryCode() != null) {
            VALUES("CountryCode", "#{countryCode,jdbcType=VARCHAR}");
        }
        
        if (record.getStreetCode() != null) {
            VALUES("StreetCode", "#{streetCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDetail() != null) {
            VALUES("Detail", "#{detail,jdbcType=VARCHAR}");
        }
        
        if (record.getPostcode() != null) {
            VALUES("Postcode", "#{postcode,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(DeliveryAddress record) {
        BEGIN();
        UPDATE("marketing_delivery_address");
        
        if (record.getMemberId() != null) {
            SET("MemberId = #{memberId,jdbcType=INTEGER}");
        }
        
        if (record.getMemberName() != null) {
            SET("MemberName = #{memberName,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            SET("Name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("Mobile = #{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getProvince() != null) {
            SET("Province = #{province,jdbcType=VARCHAR}");
        }
        
        if (record.getCity() != null) {
            SET("City = #{city,jdbcType=VARCHAR}");
        }
        
        if (record.getCountry() != null) {
            SET("Country = #{country,jdbcType=VARCHAR}");
        }
        
        if (record.getStreet() != null) {
            SET("Street = #{street,jdbcType=VARCHAR}");
        }
        
        if (record.getProvinceCode() != null) {
            SET("ProvinceCode = #{provinceCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCityCode() != null) {
            SET("CityCode = #{cityCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryCode() != null) {
            SET("CountryCode = #{countryCode,jdbcType=VARCHAR}");
        }
        
        if (record.getStreetCode() != null) {
            SET("StreetCode = #{streetCode,jdbcType=VARCHAR}");
        }
        
        if (record.getDetail() != null) {
            SET("Detail = #{detail,jdbcType=VARCHAR}");
        }
        
        if (record.getPostcode() != null) {
            SET("Postcode = #{postcode,jdbcType=VARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}