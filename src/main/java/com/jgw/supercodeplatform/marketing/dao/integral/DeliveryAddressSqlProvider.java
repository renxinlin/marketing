package com.jgw.supercodeplatform.marketing.dao.integral;

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
        
        if (record.getMemberid() != null) {
            VALUES("MemberId", "#{memberid,jdbcType=INTEGER}");
        }
        
        if (record.getMembername() != null) {
            VALUES("MemberName", "#{membername,jdbcType=VARCHAR}");
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
        
        if (record.getProvincecode() != null) {
            VALUES("ProvinceCode", "#{provincecode,jdbcType=VARCHAR}");
        }
        
        if (record.getCitycode() != null) {
            VALUES("CityCode", "#{citycode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountrycode() != null) {
            VALUES("CountryCode", "#{countrycode,jdbcType=VARCHAR}");
        }
        
        if (record.getStreetcode() != null) {
            VALUES("StreetCode", "#{streetcode,jdbcType=VARCHAR}");
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
        
        if (record.getMemberid() != null) {
            SET("MemberId = #{memberid,jdbcType=INTEGER}");
        }
        
        if (record.getMembername() != null) {
            SET("MemberName = #{membername,jdbcType=VARCHAR}");
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
        
        if (record.getProvincecode() != null) {
            SET("ProvinceCode = #{provincecode,jdbcType=VARCHAR}");
        }
        
        if (record.getCitycode() != null) {
            SET("CityCode = #{citycode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountrycode() != null) {
            SET("CountryCode = #{countrycode,jdbcType=VARCHAR}");
        }
        
        if (record.getStreetcode() != null) {
            SET("StreetCode = #{streetcode,jdbcType=VARCHAR}");
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