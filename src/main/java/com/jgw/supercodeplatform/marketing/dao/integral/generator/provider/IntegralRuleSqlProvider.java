package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;

public class IntegralRuleSqlProvider {

    public String insertSelective(IntegralRule record) {
        BEGIN();
        INSERT_INTO("marketing_integral_rule");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getTimeLimitStatus() != null) {
            VALUES("TimeLimitStatus", "#{timeLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getTimeLimit() != null) {
            VALUES("TimeLimit", "#{timeLimit,jdbcType=TIMESTAMP}");
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
        
        if (record.getCreateUserId() != null) {
            VALUES("CreateUserId", "#{createUserId,jdbcType=INTEGER}");
        }
        
        if (record.getCreateUserName() != null) {
            VALUES("CreateUserName", "#{createUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            VALUES("CreateDate", "#{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateUserId() != null) {
            VALUES("UpdateUserId", "#{updateUserId,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateUserName() != null) {
            VALUES("UpdateUserName", "#{updateUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateUserDate() != null) {
            VALUES("UpdateUserDate", "#{updateUserDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            VALUES("OrganizationName", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getIsEffective() != null) {
            VALUES("IsEffective", "#{isEffective,jdbcType=TINYINT}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRule record) {
        BEGIN();
        UPDATE("marketing_integral_rule");
        
        if (record.getTimeLimitStatus() != null) {
            SET("TimeLimitStatus = #{timeLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getTimeLimit() != null) {
            SET("TimeLimit = #{timeLimit,jdbcType=TIMESTAMP}");
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
        
        if (record.getCreateUserId() != null) {
            SET("CreateUserId = #{createUserId,jdbcType=INTEGER}");
        }
        
        if (record.getCreateUserName() != null) {
            SET("CreateUserName = #{createUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            SET("CreateDate = #{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateUserId() != null) {
            SET("UpdateUserId = #{updateUserId,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateUserName() != null) {
            SET("UpdateUserName = #{updateUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateUserDate() != null) {
            SET("UpdateUserDate = #{updateUserDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            SET("OrganizationName = #{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getIsEffective() != null) {
            SET("IsEffective = #{isEffective,jdbcType=TINYINT}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}