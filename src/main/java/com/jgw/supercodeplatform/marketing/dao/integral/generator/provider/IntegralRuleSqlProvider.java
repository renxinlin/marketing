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
            VALUES("time_limit_status", "#{timeLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getTimeLimit() != null) {
            VALUES("time_limit", "#{timeLimit,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIntegralLimitStatus() != null) {
            VALUES("integral_limit_status", "#{integralLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralLimit() != null) {
            VALUES("integral_limit", "#{integralLimit,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByRegister() != null) {
            VALUES("integral_by_register", "#{integralByRegister,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByBirthday() != null) {
            VALUES("integral_by_birthday", "#{integralByBirthday,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByFirstTime() != null) {
            VALUES("integral_by_first_time", "#{integralByFirstTime,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByRegisterStatus() != null) {
            VALUES("integral_by_register_status", "#{integralByRegisterStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByBirthdayStatus() != null) {
            VALUES("integral_by_birthday_status", "#{integralByBirthdayStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByFirstTimeStatus() != null) {
            VALUES("integral_by_first_time_status", "#{integralByFirstTimeStatus,jdbcType=BIT}");
        }
        
        if (record.getCreateUserId() != null) {
            VALUES("create_user_id", "#{createUserId,jdbcType=INTEGER}");
        }
        
        if (record.getCreateUserName() != null) {
            VALUES("create_user_name", "#{createUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            VALUES("create_date", "#{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateUserId() != null) {
            VALUES("update_user_id", "#{updateUserId,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateUserName() != null) {
            VALUES("update_user_name", "#{updateUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateUserDate() != null) {
            VALUES("update_user_date", "#{updateUserDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("organization_id", "#{organizationId,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationName() != null) {
            VALUES("organization_name", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getIsEffective() != null) {
            VALUES("is_effective", "#{isEffective,jdbcType=TINYINT}");
        }
        
        if (record.getPerage() != null) {
            VALUES("PerAge", "#{perage,jdbcType=BIT}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRule record) {
        BEGIN();
        UPDATE("marketing_integral_rule");
        
        if (record.getTimeLimitStatus() != null) {
            SET("time_limit_status = #{timeLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getTimeLimit() != null) {
            SET("time_limit = #{timeLimit,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIntegralLimitStatus() != null) {
            SET("integral_limit_status = #{integralLimitStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralLimit() != null) {
            SET("integral_limit = #{integralLimit,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByRegister() != null) {
            SET("integral_by_register = #{integralByRegister,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByBirthday() != null) {
            SET("integral_by_birthday = #{integralByBirthday,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByFirstTime() != null) {
            SET("integral_by_first_time = #{integralByFirstTime,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralByRegisterStatus() != null) {
            SET("integral_by_register_status = #{integralByRegisterStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByBirthdayStatus() != null) {
            SET("integral_by_birthday_status = #{integralByBirthdayStatus,jdbcType=BIT}");
        }
        
        if (record.getIntegralByFirstTimeStatus() != null) {
            SET("integral_by_first_time_status = #{integralByFirstTimeStatus,jdbcType=BIT}");
        }
        
        if (record.getCreateUserId() != null) {
            SET("create_user_id = #{createUserId,jdbcType=INTEGER}");
        }
        
        if (record.getCreateUserName() != null) {
            SET("create_user_name = #{createUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            SET("create_date = #{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateUserId() != null) {
            SET("update_user_id = #{updateUserId,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateUserName() != null) {
            SET("update_user_name = #{updateUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateUserDate() != null) {
            SET("update_user_date = #{updateUserDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("organization_id = #{organizationId,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationName() != null) {
            SET("organization_name = #{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getIsEffective() != null) {
            SET("is_effective = #{isEffective,jdbcType=TINYINT}");
        }
        
        if (record.getPerage() != null) {
            SET("PerAge = #{perage,jdbcType=BIT}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}