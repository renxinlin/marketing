package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;
import org.apache.ibatis.jdbc.SQL;

public class MarketingMemberCouponSqlProvider {

    public String insertSelective(MarketingMemberCoupon record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("marketing_member_coupon");
        
        if (record.getId() != null) {
            sql.VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getMemberId() != null) {
            sql.VALUES("MemberId", "#{memberId,jdbcType=BIGINT}");
        }
        
        if (record.getCouponId() != null) {
            sql.VALUES("CouponId", "#{couponId,jdbcType=BIGINT}");
        }
        
        if (record.getCouponCode() != null) {
            sql.VALUES("CouponCode", "#{couponCode,jdbcType=VARCHAR}");
        }
        
        if(record.getCouponAmount() != null) {
        	sql.VALUES("CouponAmount", "#{couponAmount,jdbcType=DOUBLE}");
        }
        
        if (record.getMemberPhone() != null) {
            sql.VALUES("MemberPhone", "#{memberPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            sql.VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductBatchId() != null) {
            sql.VALUES("ProductBatchId", "#{productBatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductBatchName() != null) {
            sql.VALUES("ProductBatchName", "#{productBatchName,jdbcType=VARCHAR}");
        }
        
        if (record.getSbatchId() != null) {
            sql.VALUES("SbatchId", "#{sbatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            sql.VALUES("ProductName", "#{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getObtainCustomerId() != null) {
            sql.VALUES("ObtainCustomerId", "#{obtainCustomerId,jdbcType=BIGINT}");
        }
        
        if (record.getDeductionDate() != null) {
            sql.VALUES("DeductionDate", "#{deductionDate,jdbcType=DATE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("CreateTime", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVerifyCustomerId() != null) {
            sql.VALUES("VerifyCustomerId", "#{verifyCustomerId,jdbcType=BIGINT}");
        }
        
        if (record.getVerifyCustomerName() != null) {
            sql.VALUES("VerifyCustomerName", "#{verifyCustomerName,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifyPersonName() != null) {
            sql.VALUES("VerifyPersonName", "#{verifyPersonName,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifyPersonPhone() != null) {
            sql.VALUES("VerifyPersonPhone", "#{verifyPersonPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifyTime() != null) {
            sql.VALUES("VerifyTime", "#{verifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVerifyPersonType() != null) {
            sql.VALUES("VerifyPersonType", "#{verifyPersonType,jdbcType=TINYINT}");
        }
        
        if (record.getUsed() != null) {
            sql.VALUES("Used", "#{used,jdbcType=TINYINT}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MarketingMemberCoupon record) {
        SQL sql = new SQL();
        sql.UPDATE("marketing_member_coupon");
        
        if (record.getMemberId() != null) {
            sql.SET("MemberId = #{memberId,jdbcType=BIGINT}");
        }
        
        if (record.getCouponId() != null) {
            sql.SET("CouponId = #{couponId,jdbcType=BIGINT}");
        }
        
        if (record.getCouponCode() != null) {
            sql.SET("CouponCode = #{couponCode,jdbcType=VARCHAR}");
        }
        
        if(record.getCouponAmount() != null) {
        	sql.SET("CouponAmount = #{couponAmount,jdbcType=DOUBLE}");
        }
        
        if (record.getMemberPhone() != null) {
            sql.SET("MemberPhone = #{memberPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            sql.SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductBatchId() != null) {
            sql.SET("ProductBatchId = #{productBatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductBatchName() != null) {
            sql.SET("ProductBatchName = #{productBatchName,jdbcType=VARCHAR}");
        }
        
        if (record.getSbatchId() != null) {
            sql.SET("SbatchId = #{sbatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            sql.SET("ProductName = #{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getObtainCustomerId() != null) {
            sql.SET("ObtainCustomerId = #{obtainCustomerId,jdbcType=BIGINT}");
        }
        
        if (record.getDeductionDate() != null) {
            sql.SET("DeductionDate = #{deductionDate,jdbcType=DATE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("CreateTime = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVerifyCustomerId() != null) {
            sql.SET("VerifyCustomerId = #{verifyCustomerId,jdbcType=BIGINT}");
        }
        
        if (record.getVerifyCustomerName() != null) {
            sql.SET("VerifyCustomerName = #{verifyCustomerName,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifyPersonName() != null) {
            sql.SET("VerifyPersonName = #{verifyPersonName,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifyPersonPhone() != null) {
            sql.SET("VerifyPersonPhone = #{verifyPersonPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getVerifyTime() != null) {
            sql.SET("VerifyTime = #{verifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVerifyPersonType() != null) {
            sql.SET("VerifyPersonType = #{verifyPersonType,jdbcType=TINYINT}");
        }
        
        if (record.getUsed() != null) {
            sql.SET("Used = #{used,jdbcType=TINYINT}");
        }
        
        sql.WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}