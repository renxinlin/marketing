package com.jgw.supercodeplatform.marketing.dao.integral;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;

public class IntegralOrderSqlProvider {

    public String insertSelective(IntegralOrder record) {
        BEGIN();
        INSERT_INTO("marketing_order");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getOrderid() != null) {
            VALUES("OrderId", "#{orderid,jdbcType=VARCHAR}");
        }
        
        if (record.getExchangeresource() != null) {
            VALUES("ExchangeResource", "#{exchangeresource,jdbcType=BIT}");
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
        
        if (record.getExchangeintegralnum() != null) {
            VALUES("ExchangeIntegralNum", "#{exchangeintegralnum,jdbcType=INTEGER}");
        }
        
        if (record.getExchangenum() != null) {
            VALUES("ExchangeNum", "#{exchangenum,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            VALUES("Name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            VALUES("Mobile", "#{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            VALUES("Address", "#{address,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            VALUES("Status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberid() != null) {
            VALUES("MemberId", "#{memberid,jdbcType=INTEGER}");
        }
        
        if (record.getMembername() != null) {
            VALUES("MemberName", "#{membername,jdbcType=VARCHAR}");
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
        
        if (record.getUpdateDate() != null) {
            VALUES("update_date", "#{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("organization_id", "#{organizationId,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationName() != null) {
            VALUES("organization_name", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralOrder record) {
        BEGIN();
        UPDATE("marketing_order");
        
        if (record.getOrderid() != null) {
            SET("OrderId = #{orderid,jdbcType=VARCHAR}");
        }
        
        if (record.getExchangeresource() != null) {
            SET("ExchangeResource = #{exchangeresource,jdbcType=BIT}");
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
        
        if (record.getExchangeintegralnum() != null) {
            SET("ExchangeIntegralNum = #{exchangeintegralnum,jdbcType=INTEGER}");
        }
        
        if (record.getExchangenum() != null) {
            SET("ExchangeNum = #{exchangenum,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            SET("Name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("Mobile = #{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            SET("Address = #{address,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("Status = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberid() != null) {
            SET("MemberId = #{memberid,jdbcType=INTEGER}");
        }
        
        if (record.getMembername() != null) {
            SET("MemberName = #{membername,jdbcType=VARCHAR}");
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
        
        if (record.getUpdateDate() != null) {
            SET("update_date = #{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            SET("organization_id = #{organizationId,jdbcType=INTEGER}");
        }
        
        if (record.getOrganizationName() != null) {
            SET("organization_name = #{organizationName,jdbcType=VARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}