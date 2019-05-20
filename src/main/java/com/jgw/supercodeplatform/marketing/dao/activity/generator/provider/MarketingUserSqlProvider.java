package com.jgw.supercodeplatform.marketing.dao.activity.generator.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;

public class MarketingUserSqlProvider {

    public String insertSelective(MarketingUser record) {
        BEGIN();
        INSERT_INTO("marketing_user");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getWxName() != null) {
            VALUES("WxName", "#{wxName,jdbcType=VARCHAR}");
        }
        
        if (record.getOpenid() != null) {
            VALUES("Openid", "#{openid,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            VALUES("Mobile", "#{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            VALUES("UserId", "#{userId,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            VALUES("UserName", "#{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            VALUES("Sex", "#{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            VALUES("Birthday", "#{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getProvinceCode() != null) {
            VALUES("ProvinceCode", "#{provinceCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountyCode() != null) {
            VALUES("CountyCode", "#{countyCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCityCode() != null) {
            VALUES("CityCode", "#{cityCode,jdbcType=VARCHAR}");
        }
        
        if (record.getProvinceName() != null) {
            VALUES("ProvinceName", "#{provinceName,jdbcType=VARCHAR}");
        }
        
        if (record.getCountyName() != null) {
            VALUES("CountyName", "#{countyName,jdbcType=VARCHAR}");
        }
        
        if (record.getCityName() != null) {
            VALUES("CityName", "#{cityName,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            VALUES("CreateDate", "#{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateDate() != null) {
            VALUES("UpdateDate", "#{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCustomerName() != null) {
            VALUES("CustomerName", "#{customerName,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            VALUES("CustomerId", "#{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getpCCcode() != null) {
            VALUES("PCCcode", "#{pCCcode,jdbcType=VARCHAR}");
        }
        
        if (record.getWechatHeadImgUrl() != null) {
            VALUES("WechatHeadImgUrl", "#{wechatHeadImgUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberType() != null) {
            VALUES("MemberType", "#{memberType,jdbcType=TINYINT}");
        }
        
        if (record.getState() != null) {
            VALUES("State", "#{state,jdbcType=TINYINT}");
        }
        
        if (record.getDeviceType() != null) {
            VALUES("DeviceType", "#{deviceType,jdbcType=TINYINT}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(MarketingUser record) {
        BEGIN();
        UPDATE("marketing_user");
        
        if (record.getWxName() != null) {
            SET("WxName = #{wxName,jdbcType=VARCHAR}");
        }
        
        if (record.getOpenid() != null) {
            SET("Openid = #{openid,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("Mobile = #{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            SET("UserId = #{userId,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            SET("UserName = #{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            SET("Sex = #{sex,jdbcType=VARCHAR}");
        }
        
        if (record.getBirthday() != null) {
            SET("Birthday = #{birthday,jdbcType=TIMESTAMP}");
        }
        
        if (record.getProvinceCode() != null) {
            SET("ProvinceCode = #{provinceCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountyCode() != null) {
            SET("CountyCode = #{countyCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCityCode() != null) {
            SET("CityCode = #{cityCode,jdbcType=VARCHAR}");
        }
        
        if (record.getProvinceName() != null) {
            SET("ProvinceName = #{provinceName,jdbcType=VARCHAR}");
        }
        
        if (record.getCountyName() != null) {
            SET("CountyName = #{countyName,jdbcType=VARCHAR}");
        }
        
        if (record.getCityName() != null) {
            SET("CityName = #{cityName,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            SET("CreateDate = #{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateDate() != null) {
            SET("UpdateDate = #{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCustomerName() != null) {
            SET("CustomerName = #{customerName,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            SET("CustomerId = #{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getpCCcode() != null) {
            SET("PCCcode = #{pCCcode,jdbcType=VARCHAR}");
        }
        
        if (record.getWechatHeadImgUrl() != null) {
            SET("WechatHeadImgUrl = #{wechatHeadImgUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberType() != null) {
            SET("MemberType = #{memberType,jdbcType=TINYINT}");
        }
        
        if (record.getState() != null) {
            SET("State = #{state,jdbcType=TINYINT}");
        }
        
        if (record.getDeviceType() != null) {
            SET("DeviceType = #{deviceType,jdbcType=TINYINT}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}