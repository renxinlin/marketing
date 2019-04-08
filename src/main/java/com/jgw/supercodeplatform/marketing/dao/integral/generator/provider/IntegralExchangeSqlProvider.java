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
        
        if (record.getExchangeobject() != null) {
            VALUES("ExchangeObject", "#{exchangeobject,jdbcType=BIT}");
        }
        
        if (record.getExchangeresource() != null) {
            VALUES("ExchangeResource", "#{exchangeresource,jdbcType=BIT}");
        }
        
        if (record.getExchangeintegral() != null) {
            VALUES("ExchangeIntegral", "#{exchangeintegral,jdbcType=INTEGER}");
        }
        
        if (record.getExchangestock() != null) {
            VALUES("ExchangeStock", "#{exchangestock,jdbcType=INTEGER}");
        }
        
        if (record.getHavestock() != null) {
            VALUES("HaveStock", "#{havestock,jdbcType=INTEGER}");
        }
        
        if (record.getCustomerlimitnum() != null) {
            VALUES("CustomerLimitNum", "#{customerlimitnum,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            VALUES("Status", "#{status,jdbcType=BIT}");
        }
        
        if (record.getPayway() != null) {
            VALUES("PayWay", "#{payway,jdbcType=BIT}");
        }
        
        if (record.getUndercarriagesetway() != null) {
            VALUES("UndercarriageSetWay", "#{undercarriagesetway,jdbcType=BIT}");
        }
        
        if (record.getUndercarriage() != null) {
            VALUES("UnderCarriage", "#{undercarriage,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStockwarning() != null) {
            VALUES("StockWarning", "#{stockwarning,jdbcType=BIT}");
        }
        
        if (record.getStockwarningnum() != null) {
            VALUES("StockWarningNum", "#{stockwarningnum,jdbcType=INTEGER}");
        }
        
        if (record.getCreateuserid() != null) {
            VALUES("CreateUserId", "#{createuserid,jdbcType=INTEGER}");
        }
        
        if (record.getCreateusername() != null) {
            VALUES("CreateUserName", "#{createusername,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            VALUES("CreateDate", "#{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateuserid() != null) {
            VALUES("UpdateUserId", "#{updateuserid,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateusername() != null) {
            VALUES("UpdateUserName", "#{updateusername,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdatedate() != null) {
            VALUES("UpdateDate", "#{updatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationid() != null) {
            VALUES("OrganizationId", "#{organizationid,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationname() != null) {
            VALUES("OrganizationName", "#{organizationname,jdbcType=VARCHAR}");
        }
        
        if (record.getProductid() != null) {
            VALUES("ProductId", "#{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getProductname() != null) {
            VALUES("ProductName", "#{productname,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuname() != null) {
            VALUES("SkuName", "#{skuname,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuurl() != null) {
            VALUES("SkuUrl", "#{skuurl,jdbcType=VARCHAR}");
        }
        
        if (record.getSkustatus() != null) {
            VALUES("SkuStatus", "#{skustatus,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralExchange record) {
        BEGIN();
        UPDATE("marketing_integral_exchange");
        
        if (record.getExchangeobject() != null) {
            SET("ExchangeObject = #{exchangeobject,jdbcType=BIT}");
        }
        
        if (record.getExchangeresource() != null) {
            SET("ExchangeResource = #{exchangeresource,jdbcType=BIT}");
        }
        
        if (record.getExchangeintegral() != null) {
            SET("ExchangeIntegral = #{exchangeintegral,jdbcType=INTEGER}");
        }
        
        if (record.getExchangestock() != null) {
            SET("ExchangeStock = #{exchangestock,jdbcType=INTEGER}");
        }
        
        if (record.getHavestock() != null) {
            SET("HaveStock = #{havestock,jdbcType=INTEGER}");
        }
        
        if (record.getCustomerlimitnum() != null) {
            SET("CustomerLimitNum = #{customerlimitnum,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            SET("Status = #{status,jdbcType=BIT}");
        }
        
        if (record.getPayway() != null) {
            SET("PayWay = #{payway,jdbcType=BIT}");
        }
        
        if (record.getUndercarriagesetway() != null) {
            SET("UndercarriageSetWay = #{undercarriagesetway,jdbcType=BIT}");
        }
        
        if (record.getUndercarriage() != null) {
            SET("UnderCarriage = #{undercarriage,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStockwarning() != null) {
            SET("StockWarning = #{stockwarning,jdbcType=BIT}");
        }
        
        if (record.getStockwarningnum() != null) {
            SET("StockWarningNum = #{stockwarningnum,jdbcType=INTEGER}");
        }
        
        if (record.getCreateuserid() != null) {
            SET("CreateUserId = #{createuserid,jdbcType=INTEGER}");
        }
        
        if (record.getCreateusername() != null) {
            SET("CreateUserName = #{createusername,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatedate() != null) {
            SET("CreateDate = #{createdate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateuserid() != null) {
            SET("UpdateUserId = #{updateuserid,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateusername() != null) {
            SET("UpdateUserName = #{updateusername,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdatedate() != null) {
            SET("UpdateDate = #{updatedate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationid() != null) {
            SET("OrganizationId = #{organizationid,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationname() != null) {
            SET("OrganizationName = #{organizationname,jdbcType=VARCHAR}");
        }
        
        if (record.getProductid() != null) {
            SET("ProductId = #{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getProductname() != null) {
            SET("ProductName = #{productname,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuname() != null) {
            SET("SkuName = #{skuname,jdbcType=VARCHAR}");
        }
        
        if (record.getSkuurl() != null) {
            SET("SkuUrl = #{skuurl,jdbcType=VARCHAR}");
        }
        
        if (record.getSkustatus() != null) {
            SET("SkuStatus = #{skustatus,jdbcType=VARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}