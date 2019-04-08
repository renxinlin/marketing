package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;

public class IntegralExchangeSqlProvider {

    public String insertSelective(IntegralExchange record) {
        BEGIN();
        INSERT_INTO("marketing_integral_exchange");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getExchangeObject() != null) {
            VALUES("ExchangeObject", "#{exchangeObject,jdbcType=BIT}");
        }
        
        if (record.getExchangeResource() != null) {
            VALUES("ExchangeResource", "#{exchangeResource,jdbcType=BIT}");
        }
        
        if (record.getExchangeIntegral() != null) {
            VALUES("ExchangeIntegral", "#{exchangeIntegral,jdbcType=INTEGER}");
        }
        
        if (record.getExchangeStock() != null) {
            VALUES("ExchangeStock", "#{exchangeStock,jdbcType=INTEGER}");
        }
        
        if (record.getHaveStock() != null) {
            VALUES("HaveStock", "#{haveStock,jdbcType=INTEGER}");
        }
        
        if (record.getCustomerLimitNum() != null) {
            VALUES("CustomerLimitNum", "#{customerLimitNum,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            VALUES("Status", "#{status,jdbcType=BIT}");
        }
        
        if (record.getPayWay() != null) {
            VALUES("PayWay", "#{payWay,jdbcType=BIT}");
        }
        
        if (record.getUndercarriageSetWay() != null) {
            VALUES("UndercarriageSetWay", "#{undercarriageSetWay,jdbcType=BIT}");
        }
        
        if (record.getUnderCarriage() != null) {
            VALUES("UnderCarriage", "#{underCarriage,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStockWarning() != null) {
            VALUES("StockWarning", "#{stockWarning,jdbcType=BIT}");
        }
        
        if (record.getStockWarningNum() != null) {
            VALUES("StockWarningNum", "#{stockWarningNum,jdbcType=INTEGER}");
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
        
        if (record.getUpdateDate() != null) {
            VALUES("UpdateDate", "#{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            VALUES("OrganizationName", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            VALUES("ProductName", "#{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuName() != null) {
            VALUES("SkuName", "#{skuName,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuUrl() != null) {
            VALUES("SkuUrl", "#{skuUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuStatus() != null) {
            VALUES("SkuStatus", "#{skuStatus,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralExchange record) {
        BEGIN();
        UPDATE("marketing_integral_exchange");
        
        if (record.getExchangeObject() != null) {
            SET("ExchangeObject = #{exchangeObject,jdbcType=BIT}");
        }
        
        if (record.getExchangeResource() != null) {
            SET("ExchangeResource = #{exchangeResource,jdbcType=BIT}");
        }
        
        if (record.getExchangeIntegral() != null) {
            SET("ExchangeIntegral = #{exchangeIntegral,jdbcType=INTEGER}");
        }
        
        if (record.getExchangeStock() != null) {
            SET("ExchangeStock = #{exchangeStock,jdbcType=INTEGER}");
        }
        
        if (record.getHaveStock() != null) {
            SET("HaveStock = #{haveStock,jdbcType=INTEGER}");
        }
        
        if (record.getCustomerLimitNum() != null) {
            SET("CustomerLimitNum = #{customerLimitNum,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            SET("Status = #{status,jdbcType=BIT}");
        }
        
        if (record.getPayWay() != null) {
            SET("PayWay = #{payWay,jdbcType=BIT}");
        }
        
        if (record.getUndercarriageSetWay() != null) {
            SET("UndercarriageSetWay = #{undercarriageSetWay,jdbcType=BIT}");
        }
        
        if (record.getUnderCarriage() != null) {
            SET("UnderCarriage = #{underCarriage,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStockWarning() != null) {
            SET("StockWarning = #{stockWarning,jdbcType=BIT}");
        }
        
        if (record.getStockWarningNum() != null) {
            SET("StockWarningNum = #{stockWarningNum,jdbcType=INTEGER}");
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
        
        if (record.getUpdateDate() != null) {
            SET("UpdateDate = #{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            SET("OrganizationName = #{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            SET("ProductName = #{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuName() != null) {
            SET("SkuName = #{skuName,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuUrl() != null) {
            SET("SkuUrl = #{skuUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuStatus() != null) {
            SET("SkuStatus = #{skuStatus,jdbcType=VARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}