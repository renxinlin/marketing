package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import com.jgw.supercodeplatform.marketing.pojo.integral.ExchangeStatistics;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class ExchangeStatisticsSqlProvider {

    public String insertSelective(ExchangeStatistics record) {
        BEGIN();
        INSERT_INTO("marketing_member_exchange_statistics");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberId() != null) {
            VALUES("MemberId", "#{memberId,jdbcType=BIGINT}");
        }
        
        if (record.getExchangeNum() != null) {
            VALUES("ExchangeNum", "#{exchangeNum,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(ExchangeStatistics record) {
        BEGIN();
        UPDATE("marketing_member_exchange_statistics");
        
        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductId() != null) {
            SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberId() != null) {
            SET("MemberId = #{memberId,jdbcType=BIGINT}");
        }
        
        if (record.getExchangeNum() != null) {
            SET("ExchangeNum = #{exchangeNum,jdbcType=INTEGER}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}