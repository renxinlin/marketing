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
        
        if (record.getCustomertype() != null) {
            VALUES("CustomerType", "#{customertype,jdbcType=BIT}");
        }
        
        if (record.getCustomerid() != null) {
            VALUES("CustomerId", "#{customerid,jdbcType=INTEGER}");
        }
        
        if (record.getCustomername() != null) {
            VALUES("CustomerName", "#{customername,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            VALUES("Mobile", "#{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralreasoncode() != null) {
            VALUES("IntegralReasonCode", "#{integralreasoncode,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralreason() != null) {
            VALUES("IntegralReason", "#{integralreason,jdbcType=VARCHAR}");
        }
        
        if (record.getProductid() != null) {
            VALUES("ProductId", "#{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getProductname() != null) {
            VALUES("ProductName", "#{productname,jdbcType=VARCHAR}");
        }
        
        if (record.getOutercodeid() != null) {
            VALUES("OuterCodeId", "#{outercodeid,jdbcType=VARCHAR}");
        }
        
        if (record.getCodetypeid() != null) {
            VALUES("CodeTypeId", "#{codetypeid,jdbcType=VARCHAR}");
        }
        
        if (record.getRegisterstore() != null) {
            VALUES("RegisterStore", "#{registerstore,jdbcType=VARCHAR}");
        }
        
        if (record.getRegisterstoreid() != null) {
            VALUES("RegisterStoreId", "#{registerstoreid,jdbcType=INTEGER}");
        }
        
        if (record.getCreatedate() != null) {
            VALUES("CreateDate", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationid() != null) {
            VALUES("OrganizationId", "#{organizationid,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationname() != null) {
            VALUES("OrganizationName", "#{organizationname,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralnum() != null) {
            VALUES("IntegralNum", "#{integralnum,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralRecord record) {
        BEGIN();
        UPDATE("marketing_integral_record");
        
        if (record.getCustomertype() != null) {
            SET("CustomerType = #{customertype,jdbcType=BIT}");
        }
        
        if (record.getCustomerid() != null) {
            SET("CustomerId = #{customerid,jdbcType=INTEGER}");
        }
        
        if (record.getCustomername() != null) {
            SET("CustomerName = #{customername,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("Mobile = #{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralreasoncode() != null) {
            SET("IntegralReasonCode = #{integralreasoncode,jdbcType=INTEGER}");
        }
        
        if (record.getIntegralreason() != null) {
            SET("IntegralReason = #{integralreason,jdbcType=VARCHAR}");
        }
        
        if (record.getProductid() != null) {
            SET("ProductId = #{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getProductname() != null) {
            SET("ProductName = #{productname,jdbcType=VARCHAR}");
        }
        
        if (record.getOutercodeid() != null) {
            SET("OuterCodeId = #{outercodeid,jdbcType=VARCHAR}");
        }
        
        if (record.getCodetypeid() != null) {
            SET("CodeTypeId = #{codetypeid,jdbcType=VARCHAR}");
        }
        
        if (record.getRegisterstore() != null) {
            SET("RegisterStore = #{registerstore,jdbcType=VARCHAR}");
        }
        
        if (record.getRegisterstoreid() != null) {
            SET("RegisterStoreId = #{registerstoreid,jdbcType=INTEGER}");
        }
        
        if (record.getCreatedate() != null) {
            SET("CreateDate = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationid() != null) {
            SET("OrganizationId = #{organizationid,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationname() != null) {
            SET("OrganizationName = #{organizationname,jdbcType=VARCHAR}");
        }
        
        if (record.getIntegralnum() != null) {
            SET("IntegralNum = #{integralnum,jdbcType=INTEGER}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}