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
        
        if (record.getIntegralruleid() != null) {
            VALUES("IntegralRuleId", "#{integralruleid,jdbcType=INTEGER}");
        }
        
        if (record.getProductid() != null) {
            VALUES("ProductId", "#{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getProductname() != null) {
            VALUES("ProductName", "#{productname,jdbcType=VARCHAR}");
        }
        
        if (record.getProductprice() != null) {
            VALUES("ProductPrice", "#{productprice,jdbcType=REAL}");
        }
        
        if (record.getRewardobject() != null) {
            VALUES("RewardObject", "#{rewardobject,jdbcType=BIT}");
        }
        
        if (record.getRewardrule() != null) {
            VALUES("RewardRule", "#{rewardrule,jdbcType=BIT}");
        }
        
        if (record.getPerconsume() != null) {
            VALUES("PerConsume", "#{perconsume,jdbcType=REAL}");
        }
        
        if (record.getRewardintegral() != null) {
            VALUES("RewardIntegral", "#{rewardintegral,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRuleProduct record) {
        BEGIN();
        UPDATE("marketing_integral_rule_product");
        
        if (record.getIntegralruleid() != null) {
            SET("IntegralRuleId = #{integralruleid,jdbcType=INTEGER}");
        }
        
        if (record.getProductid() != null) {
            SET("ProductId = #{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getProductname() != null) {
            SET("ProductName = #{productname,jdbcType=VARCHAR}");
        }
        
        if (record.getProductprice() != null) {
            SET("ProductPrice = #{productprice,jdbcType=REAL}");
        }
        
        if (record.getRewardobject() != null) {
            SET("RewardObject = #{rewardobject,jdbcType=BIT}");
        }
        
        if (record.getRewardrule() != null) {
            SET("RewardRule = #{rewardrule,jdbcType=BIT}");
        }
        
        if (record.getPerconsume() != null) {
            SET("PerConsume = #{perconsume,jdbcType=REAL}");
        }
        
        if (record.getRewardintegral() != null) {
            SET("RewardIntegral = #{rewardintegral,jdbcType=INTEGER}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}