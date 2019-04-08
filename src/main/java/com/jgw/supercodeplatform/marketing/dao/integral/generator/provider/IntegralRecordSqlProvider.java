package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;

public class IntegralRecordSqlProvider {

    public String insertSelective(IntegralRecord record) {
        BEGIN();
        INSERT_INTO("marketing_integral_record");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getMemberType() != null) {
            VALUES("MemberType", "#{memberType,jdbcType=TINYINT}");
        }
        
        if (record.getMemberId() != null) {
            VALUES("MemberId", "#{memberId,jdbcType=INTEGER}");
        }
        
        if (record.getMemberName() != null) {
            VALUES("MemberName", "#{memberName,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            VALUES("Mobile", "#{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralReasonCode() != null) {
            VALUES("IntegralReasonCode", "#{integralReasonCode,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralReason() != null) {
            VALUES("IntegralReason", "#{integralReason,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            VALUES("ProductName", "#{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getOuterCodeId() != null) {
            VALUES("OuterCodeId", "#{outerCodeId,jdbcType=VARCHAR}");
        }
        
        if (record.getCodeTypeId() != null) {
            VALUES("CodeTypeId", "#{codeTypeId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerName() != null) {
            VALUES("CustomerName", "#{customerName,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            VALUES("CustomerId", "#{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            VALUES("CreateDate", "#{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            VALUES("OrganizationName", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralNum() != null) {
            VALUES("IntegralNum", "#{integralNum,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRecord record) {
        BEGIN();
        UPDATE("marketing_integral_record");
        
        if (record.getMemberType() != null) {
            SET("MemberType = #{memberType,jdbcType=TINYINT}");
        }
        
        if (record.getMemberId() != null) {
            SET("MemberId = #{memberId,jdbcType=INTEGER}");
        }
        
        if (record.getMemberName() != null) {
            SET("MemberName = #{memberName,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("Mobile = #{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralReasonCode() != null) {
            SET("IntegralReasonCode = #{integralReasonCode,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralReason() != null) {
            SET("IntegralReason = #{integralReason,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            SET("ProductName = #{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getOuterCodeId() != null) {
            SET("OuterCodeId = #{outerCodeId,jdbcType=VARCHAR}");
        }
        
        if (record.getCodeTypeId() != null) {
            SET("CodeTypeId = #{codeTypeId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerName() != null) {
            SET("CustomerName = #{customerName,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerId() != null) {
            SET("CustomerId = #{customerId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            SET("CreateDate = #{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            SET("OrganizationName = #{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralNum() != null) {
            SET("IntegralNum = #{integralNum,jdbcType=INTEGER}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}