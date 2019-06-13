package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberProductIntegral;
import org.apache.ibatis.jdbc.SQL;

public class MarketingMemberProductIntegralSqlProvider {

    public String insertSelective(MarketingMemberProductIntegral record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("marketing_member_product_integral");
        
        if (record.getId() != null) {
            sql.VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getMemberId() != null) {
            sql.VALUES("MemberId", "#{memberId,jdbcType=BIGINT}");
        }
        
        if (record.getProductId() != null) {
            sql.VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductBatchId() != null) {
            sql.VALUES("ProductBatchId", "#{productBatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getSbatchId() != null) {
            sql.VALUES("SbatchId", "#{sbatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getAccrueIntegral() != null) {
            sql.VALUES("AccrueIntegral", "#{accrueIntegral,jdbcType=BIGINT}");
        }
        
        if (record.getOrganizationId() != null) {
            sql.VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            sql.VALUES("OrganizationName", "#{organizationName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("CreateTime", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("UpdateTime", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MarketingMemberProductIntegral record) {
        SQL sql = new SQL();
        sql.UPDATE("marketing_member_product_integral");
        
        if (record.getMemberId() != null) {
            sql.SET("MemberId = #{memberId,jdbcType=BIGINT}");
        }
        
        if (record.getProductId() != null) {
            sql.SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductBatchId() != null) {
            sql.SET("ProductBatchId = #{productBatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getSbatchId() != null) {
            sql.SET("SbatchId = #{sbatchId,jdbcType=VARCHAR}");
        }
        
        if (record.getAccrueIntegral() != null) {
            sql.SET("AccrueIntegral = #{accrueIntegral,jdbcType=BIGINT}");
        }
        
        if (record.getOrganizationId() != null) {
            sql.SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getOrganizationName() != null) {
            sql.SET("OrganizationName = #{organizationName,jdbcType=VARCHAR}");
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