package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class IntegralRuleSqlProvider {

    public String insertSelective(IntegralRule record) {
        BEGIN();
        INSERT_INTO("marketing_integral_rule");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getTimeLimitStatus() != null) {
            VALUES("TimeLimitStatus", "#{timeLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getTimeLimitDate() != null) {
            VALUES("TimeLimitDate", "#{timeLimitDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIntegralLimitStatus() != null) {
            VALUES("IntegralLimitStatus", "#{integralLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralLimit() != null) {
            VALUES("IntegralLimit", "#{integralLimit,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralLimitAge() != null) {
            VALUES("IntegralLimitAge", "#{integralLimitAge,jdbcType=BIT}");
        }
        
        if (record.getIntegralByRegister() != null) {
            VALUES("IntegralByRegister", "#{integralByRegister,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByBirthday() != null) {
            VALUES("IntegralByBirthday", "#{integralByBirthday,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByFirstTime() != null) {
            VALUES("IntegralByFirstTime", "#{integralByFirstTime,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByRegisterStatus() != null) {
            VALUES("IntegralByRegisterStatus", "#{integralByRegisterStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByBirthdayStatus() != null) {
            VALUES("IntegralByBirthdayStatus", "#{integralByBirthdayStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByFirstTimeStatus() != null) {
            VALUES("IntegralByFirstTimeStatus", "#{integralByFirstTimeStatus,jdbcType=BIT}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            VALUES("OrganizationName", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRule record) {
        BEGIN();
        UPDATE("marketing_integral_rule");
        
        if (record.getTimeLimitStatus() != null) {
            SET("TimeLimitStatus = #{timeLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getTimeLimitDate() != null) {
            SET("TimeLimitDate = #{timeLimitDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIntegralLimitStatus() != null) {
            SET("IntegralLimitStatus = #{integralLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralLimit() != null) {
            SET("IntegralLimit = #{integralLimit,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralLimitAge() != null) {
            SET("IntegralLimitAge = #{integralLimitAge,jdbcType=BIT}");
        }
        
        if (record.getIntegralByRegister() != null) {
            SET("IntegralByRegister = #{integralByRegister,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByBirthday() != null) {
            SET("IntegralByBirthday = #{integralByBirthday,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByFirstTime() != null) {
            SET("IntegralByFirstTime = #{integralByFirstTime,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByRegisterStatus() != null) {
            SET("IntegralByRegisterStatus = #{integralByRegisterStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByBirthdayStatus() != null) {
            SET("IntegralByBirthdayStatus = #{integralByBirthdayStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByFirstTimeStatus() != null) {
            SET("IntegralByFirstTimeStatus = #{integralByFirstTimeStatus,jdbcType=BIT}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            SET("OrganizationName = #{organizationName,jdbcType=VARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}