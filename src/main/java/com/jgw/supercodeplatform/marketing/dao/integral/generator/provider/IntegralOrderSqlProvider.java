package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class IntegralOrderSqlProvider {

    public String insertSelective(IntegralOrder record) {
        BEGIN();
        INSERT_INTO("marketing_order");

        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getOrderId() != null) {
            VALUES("OrderId", "#{orderId,jdbcType=VARCHAR}");
        }

        if (record.getExchangeResource() != null) {
            VALUES("ExchangeResource", "#{exchangeResource,jdbcType=TINYINT}");
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

        if (record.getExchangeIntegralNum() != null) {
            VALUES("ExchangeIntegralNum", "#{exchangeIntegralNum,jdbcType=INTEGER}");
        }

        if (record.getExchangeNum() != null) {
            VALUES("ExchangeNum", "#{exchangeNum,jdbcType=INTEGER}");
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
            VALUES("Status", "#{status,jdbcType=TINYINT}");
        }

        if (record.getMemberId() != null) {
            VALUES("MemberId", "#{memberId,jdbcType=BIGINT}");
        }

        if (record.getMemberName() != null) {
            VALUES("MemberName", "#{memberName,jdbcType=VARCHAR}");
        }

        if (record.getCreateDate() != null) {
            VALUES("CreateDate", "#{createDate,jdbcType=TIMESTAMP}");
        }

        if (record.getDeliveryDate() != null) {
            VALUES("DeliveryDate", "#{deliveryDate,jdbcType=TIMESTAMP}");
        }

        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationName() != null) {
            VALUES("OrganizationName", "#{organizationName,jdbcType=VARCHAR}");
        }

        if (record.getShowPrice() != null) {
            VALUES("ShowPrice", "#{showPrice,jdbcType=REAL}");
        }

        if (record.getProductPic() != null) {
            VALUES("ProductPic", "#{productPic,jdbcType=VARCHAR}");
        }

        if (record.getSkuId() != null) {
            VALUES("SkuId", "#{skuId,jdbcType=VARCHAR}");
        }

        return SQL();
    }

    public String updateByPrimaryKeySelective(IntegralOrder record) {
        BEGIN();
        UPDATE("marketing_order");

        if (record.getOrderId() != null) {
            SET("OrderId = #{orderId,jdbcType=VARCHAR}");
        }

        if (record.getExchangeResource() != null) {
            SET("ExchangeResource = #{exchangeResource,jdbcType=TINYINT}");
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

        if (record.getExchangeIntegralNum() != null) {
            SET("ExchangeIntegralNum = #{exchangeIntegralNum,jdbcType=INTEGER}");
        }

        if (record.getExchangeNum() != null) {
            SET("ExchangeNum = #{exchangeNum,jdbcType=INTEGER}");
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
            SET("Status = #{status,jdbcType=TINYINT}");
        }

        if (record.getMemberId() != null) {
            SET("MemberId = #{memberId,jdbcType=BIGINT}");
        }

        if (record.getMemberName() != null) {
            SET("MemberName = #{memberName,jdbcType=VARCHAR}");
        }

        if (record.getCreateDate() != null) {
            SET("CreateDate = #{createDate,jdbcType=TIMESTAMP}");
        }

        if (record.getDeliveryDate() != null) {
            SET("DeliveryDate = #{deliveryDate,jdbcType=TIMESTAMP}");
        }

        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }

        if (record.getOrganizationName() != null) {
            SET("OrganizationName = #{organizationName,jdbcType=VARCHAR}");
        }

        if (record.getShowPrice() != null) {
            SET("ShowPrice = #{showPrice,jdbcType=REAL}");
        }

        if (record.getProductPic() != null) {
            SET("ProductPic = #{productPic,jdbcType=VARCHAR}");
        }

        if (record.getSkuId() != null) {
            SET("SkuId = #{skuId,jdbcType=VARCHAR}");
        }

        WHERE("Id = #{id,jdbcType=BIGINT}");

        return SQL();
    }
}