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
        
        if (record.getProductid() != null) {
            VALUES("ProductId", "#{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleproductname() != null) {
            VALUES("UnsaleProductName", "#{unsaleproductname,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleproductpic() != null) {
            VALUES("UnsaleProductPic", "#{unsaleproductpic,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleproductskunum() != null) {
            VALUES("UnsaleProductSkuNum", "#{unsaleproductskunum,jdbcType=INTEGER}");
        }
        
        if (record.getUnsaleproductskuinfo() != null) {
            VALUES("UnsaleProductSkuInfo", "#{unsaleproductskuinfo,jdbcType=VARCHAR}");
        }
        
        if (record.getShowprice() != null) {
            VALUES("ShowPrice", "#{showprice,jdbcType=REAL}");
        }
        
        if (record.getRealprice() != null) {
            VALUES("RealPrice", "#{realprice,jdbcType=REAL}");
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
        
        if (record.getCreateuserid() != null) {
            VALUES("CreateUserId", "#{createuserid,jdbcType=INTEGER}");
        }
        
        if (record.getCreateusername() != null) {
            VALUES("CreateUserName", "#{createusername,jdbcType=VARCHAR}");
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
        
        if (record.getDetail() != null) {
            VALUES("Detail", "#{detail,jdbcType=LONGVARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(ProductUnsale record) {
        BEGIN();
        UPDATE("marketing_product_unsale");
        
        if (record.getProductid() != null) {
            SET("ProductId = #{productid,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleproductname() != null) {
            SET("UnsaleProductName = #{unsaleproductname,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleproductpic() != null) {
            SET("UnsaleProductPic = #{unsaleproductpic,jdbcType=VARCHAR}");
        }
        
        if (record.getUnsaleproductskunum() != null) {
            SET("UnsaleProductSkuNum = #{unsaleproductskunum,jdbcType=INTEGER}");
        }
        
        if (record.getUnsaleproductskuinfo() != null) {
            SET("UnsaleProductSkuInfo = #{unsaleproductskuinfo,jdbcType=VARCHAR}");
        }
        
        if (record.getShowprice() != null) {
            SET("ShowPrice = #{showprice,jdbcType=REAL}");
        }
        
        if (record.getRealprice() != null) {
            SET("RealPrice = #{realprice,jdbcType=REAL}");
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
        
        if (record.getCreateuserid() != null) {
            SET("CreateUserId = #{createuserid,jdbcType=INTEGER}");
        }
        
        if (record.getCreateusername() != null) {
            SET("CreateUserName = #{createusername,jdbcType=VARCHAR}");
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
        
        if (record.getDetail() != null) {
            SET("Detail = #{detail,jdbcType=LONGVARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}