package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;

public class IntegralRuleProductSqlProvider {

    public String insertSelective(IntegralRuleProduct record) {
        BEGIN();
        INSERT_INTO("marketing_integral_rule_product");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getIntegralRuleId() != null) {
            VALUES("IntegralRuleId", "#{integralRuleId,jdbcType=INTEGER}");
        }
        
        if (record.getProductId() != null) {
            VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            VALUES("ProductName", "#{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getProductPrice() != null) {
            VALUES("ProductPrice", "#{productPrice,jdbcType=REAL}");
        }
        
        if (record.getMemberType() != null) {
            VALUES("MemberType", "#{memberType,jdbcType=TINYINT}");
        }
        
        if (record.getRewardRule() != null) {
            VALUES("RewardRule", "#{rewardRule,jdbcType=TINYINT}");
        }
        
        if (record.getPerConsume() != null) {
            VALUES("PerConsume", "#{perConsume,jdbcType=REAL}");
        }
        
        if (record.getRewardIntegral() != null) {
            VALUES("RewardIntegral", "#{rewardIntegral,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRuleProduct record) {
        BEGIN();
        UPDATE("marketing_integral_rule_product");
        
        if (record.getIntegralRuleId() != null) {
            SET("IntegralRuleId = #{integralRuleId,jdbcType=INTEGER}");
        }
        
        if (record.getProductId() != null) {
            SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            SET("ProductName = #{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getProductPrice() != null) {
            SET("ProductPrice = #{productPrice,jdbcType=REAL}");
        }
        
        if (record.getMemberType() != null) {
            SET("MemberType = #{memberType,jdbcType=TINYINT}");
        }
        
        if (record.getRewardRule() != null) {
            SET("RewardRule = #{rewardRule,jdbcType=TINYINT}");
        }
        
        if (record.getPerConsume() != null) {
            SET("PerConsume = #{perConsume,jdbcType=REAL}");
        }
        
        if (record.getRewardIntegral() != null) {
            SET("RewardIntegral = #{rewardIntegral,jdbcType=INTEGER}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}