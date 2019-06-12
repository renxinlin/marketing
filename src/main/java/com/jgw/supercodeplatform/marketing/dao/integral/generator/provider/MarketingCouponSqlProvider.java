package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingCoupon;
import org.apache.ibatis.jdbc.SQL;

public class MarketingCouponSqlProvider {

    public String insertSelective(MarketingCoupon record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("marketing_coupon");
        
        if (record.getId() != null) {
            sql.VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getActivitySetId() != null) {
            sql.VALUES("ActivitySetId", "#{activitySetId,jdbcType=BIGINT}");
        }
        
        if (record.getOrganizationId() != null) {
            sql.VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            sql.VALUES("OrganizationName", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getCouponAmount() != null) {
            sql.VALUES("CouponAmount", "#{couponAmount,jdbcType=DOUBLE}");
        }
        
        if (record.getDeductionStartDate() != null) {
            sql.VALUES("DeductionStartDate", "#{deductionStartDate,jdbcType=DATE}");
        }
        
        if (record.getDeductionEndDate() != null) {
            sql.VALUES("DeductionEndDate", "#{deductionEndDate,jdbcType=DATE}");
        }
        
        if (record.getDeductionProductType() != null) {
            sql.VALUES("DeductionProductType", "#{deductionProductType,jdbcType=TINYINT}");
        }
        
        if (record.getDeductionChannelType() != null) {
            sql.VALUES("DeductionChannelType", "#{deductionChannelType,jdbcType=TINYINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("CreateTime", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("UpdateTime", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MarketingCoupon record) {
        SQL sql = new SQL();
        sql.UPDATE("marketing_coupon");
        
        if (record.getActivitySetId() != null) {
            sql.SET("ActivitySetId = #{activitySetId,jdbcType=BIGINT}");
        }
        
        if (record.getOrganizationId() != null) {
            sql.SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            sql.SET("OrganizationName = #{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getCouponAmount() != null) {
            sql.SET("CouponAmount = #{couponAmount,jdbcType=DOUBLE}");
        }
        
        if (record.getDeductionStartDate() != null) {
            sql.SET("DeductionStartDate = #{deductionStartDate,jdbcType=DATE}");
        }
        
        if (record.getDeductionEndDate() != null) {
            sql.SET("DeductionEndDate = #{deductionEndDate,jdbcType=DATE}");
        }
        
        if (record.getDeductionProductType() != null) {
            sql.SET("DeductionProductType = #{deductionProductType,jdbcType=TINYINT}");
        }
        
        if (record.getDeductionChannelType() != null) {
            sql.SET("DeductionChannelType = #{deductionChannelType,jdbcType=TINYINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("CreateTime = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("UpdateTime = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}