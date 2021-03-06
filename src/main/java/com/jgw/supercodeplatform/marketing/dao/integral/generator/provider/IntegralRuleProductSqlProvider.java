package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class IntegralRuleProductSqlProvider {

    public String insertSelective(IntegralRuleProduct record) {
        BEGIN();
        INSERT_INTO("marketing_integral_rule_product");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getIntegralRuleId() != null) {
            VALUES("IntegralRuleId", "#{integralRuleId,jdbcType=BIGINT}");
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
            VALUES("MemberType", "#{memberType,jdbcType=BIT}");
        }
        
        if (record.getRewardRule() != null) {
            VALUES("RewardRule", "#{rewardRule,jdbcType=BIT}");
        }
        
        if (record.getPerConsume() != null) {
            VALUES("PerConsume", "#{perConsume,jdbcType=REAL}");
        }
        
        if (record.getRewardIntegral() != null) {
            VALUES("RewardIntegral", "#{rewardIntegral,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRuleProduct record) {
        BEGIN();
        UPDATE("marketing_integral_rule_product");
        
        if (record.getIntegralRuleId() != null) {
            SET("IntegralRuleId = #{integralRuleId,jdbcType=BIGINT}");
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
            SET("MemberType = #{memberType,jdbcType=BIT}");
        }
        
        if (record.getRewardRule() != null) {
            SET("RewardRule = #{rewardRule,jdbcType=BIT}");
        }
        
        if (record.getPerConsume() != null) {
            SET("PerConsume = #{perConsume,jdbcType=REAL}");
        }
        
        if (record.getRewardIntegral() != null) {
            SET("RewardIntegral = #{rewardIntegral,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}