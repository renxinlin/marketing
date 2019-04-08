package com.jgw.supercodeplatform.marketing.dao.integral.generator.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;

public class ProductUnsaleSqlProvider {

    public String insertSelective(ProductUnsale record) {
        BEGIN();
        INSERT_INTO("marketing_product_unsale");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getProductId() != null) {
            VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleProductName() != null) {
            VALUES("UnsaleProductName", "#{unsaleProductName,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleProductPic() != null) {
            VALUES("UnsaleProductPic", "#{unsaleProductPic,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleProductSkuNum() != null) {
            VALUES("UnsaleProductSkuNum", "#{unsaleProductSkuNum,jdbcType=INTEGER}");
        }
        
        if (record.getUnsaleProductSkuInfo() != null) {
            VALUES("UnsaleProductSkuInfo", "#{unsaleProductSkuInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getShowPrice() != null) {
            VALUES("ShowPrice", "#{showPrice,jdbcType=REAL}");
        }
        
        if (record.getRealPrice() != null) {
            VALUES("RealPrice", "#{realPrice,jdbcType=REAL}");
        }
        
        if (record.getUpdateUserId() != null) {
            VALUES("UpdateUserId", "#{updateUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateUserName() != null) {
            VALUES("UpdateUserName", "#{updateUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateDate() != null) {
            VALUES("UpdateDate", "#{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            VALUES("CreateUserId", "#{createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateUserName() != null) {
            VALUES("CreateUserName", "#{createUserName,jdbcType=VARCHAR}");
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
        
        if (record.getDetail() != null) {
            VALUES("Detail", "#{detail,jdbcType=LONGVARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(ProductUnsale record) {
        BEGIN();
        UPDATE("marketing_product_unsale");
        
        if (record.getProductId() != null) {
            SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleProductName() != null) {
            SET("UnsaleProductName = #{unsaleProductName,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleProductPic() != null) {
            SET("UnsaleProductPic = #{unsaleProductPic,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleProductSkuNum() != null) {
            SET("UnsaleProductSkuNum = #{unsaleProductSkuNum,jdbcType=INTEGER}");
        }
        
        if (record.getUnsaleProductSkuInfo() != null) {
            SET("UnsaleProductSkuInfo = #{unsaleProductSkuInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getShowPrice() != null) {
            SET("ShowPrice = #{showPrice,jdbcType=REAL}");
        }
        
        if (record.getRealPrice() != null) {
            SET("RealPrice = #{realPrice,jdbcType=REAL}");
        }
        
        if (record.getUpdateUserId() != null) {
            SET("UpdateUserId = #{updateUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateUserName() != null) {
            SET("UpdateUserName = #{updateUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateDate() != null) {
            SET("UpdateDate = #{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            SET("CreateUserId = #{createUserId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateUserName() != null) {
            SET("CreateUserName = #{createUserName,jdbcType=VARCHAR}");
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
        
        if (record.getDetail() != null) {
            SET("Detail = #{detail,jdbcType=LONGVARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}