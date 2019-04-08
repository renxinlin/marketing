package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface ProductUnsaleMapper {
    @Delete({
        "delete from marketing_product_unsale",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_product_unsale (Id, ProductId, ",
        "UnsaleProductName, UnsaleProductPic, ",
        "UnsaleProductSkuNum, UnsaleProductSkuInfo, ",
        "ShowPrice, RealPrice, ",
        "UpdateUserId, UpdateUserName, ",
        "UpdateDate, CreateUserId, ",
        "CreateUserName, CreateDate, ",
        "OrganizationId, OrganizationName, ",
        "Detail)",
        "values (#{id,jdbcType=BIGINT}, #{productid,jdbcType=VARCHAR}, ",
        "#{unsaleproductname,jdbcType=VARCHAR}, #{unsaleproductpic,jdbcType=VARCHAR}, ",
        "#{unsaleproductskunum,jdbcType=INTEGER}, #{unsaleproductskuinfo,jdbcType=VARCHAR}, ",
        "#{showprice,jdbcType=REAL}, #{realprice,jdbcType=REAL}, ",
        "#{updateuserid,jdbcType=INTEGER}, #{updateusername,jdbcType=VARCHAR}, ",
        "#{updatedate,jdbcType=TIMESTAMP}, #{createuserid,jdbcType=INTEGER}, ",
        "#{createusername,jdbcType=VARCHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{organizationid,jdbcType=INTEGER}, #{organizationname,jdbcType=VARCHAR}, ",
        "#{detail,jdbcType=LONGVARCHAR})"
    })
    int insert(ProductUnsale record);

    @InsertProvider(type=ProductUnsaleSqlProvider.class, method="insertSelective")
    int insertSelective(ProductUnsale record);

    @Select({
        "select",
        "Id, ProductId, UnsaleProductName, UnsaleProductPic, UnsaleProductSkuNum, UnsaleProductSkuInfo, ",
        "ShowPrice, RealPrice, UpdateUserId, UpdateUserName, UpdateDate, CreateUserId, ",
        "CreateUserName, CreateDate, OrganizationId, OrganizationName, Detail",
        "from marketing_product_unsale",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ProductId", property="productid", jdbcType=JdbcType.VARCHAR),
        @Result(column="UnsaleProductName", property="unsaleproductname", jdbcType=JdbcType.VARCHAR),
        @Result(column="UnsaleProductPic", property="unsaleproductpic", jdbcType=JdbcType.VARCHAR),
        @Result(column="UnsaleProductSkuNum", property="unsaleproductskunum", jdbcType=JdbcType.INTEGER),
        @Result(column="UnsaleProductSkuInfo", property="unsaleproductskuinfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="ShowPrice", property="showprice", jdbcType=JdbcType.REAL),
        @Result(column="RealPrice", property="realprice", jdbcType=JdbcType.REAL),
        @Result(column="UpdateUserId", property="updateuserid", jdbcType=JdbcType.INTEGER),
        @Result(column="UpdateUserName", property="updateusername", jdbcType=JdbcType.VARCHAR),
        @Result(column="UpdateDate", property="updatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CreateUserId", property="createuserid", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateUserName", property="createusername", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationid", jdbcType=JdbcType.INTEGER),
        @Result(column="OrganizationName", property="organizationname", jdbcType=JdbcType.VARCHAR),
        @Result(column="Detail", property="detail", jdbcType=JdbcType.LONGVARCHAR)
    })
    ProductUnsale selectByPrimaryKey(Long id);

    @UpdateProvider(type=ProductUnsaleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ProductUnsale record);

    @Update({
        "update marketing_product_unsale",
        "set ProductId = #{productid,jdbcType=VARCHAR},",
          "UnsaleProductName = #{unsaleproductname,jdbcType=VARCHAR},",
          "UnsaleProductPic = #{unsaleproductpic,jdbcType=VARCHAR},",
          "UnsaleProductSkuNum = #{unsaleproductskunum,jdbcType=INTEGER},",
          "UnsaleProductSkuInfo = #{unsaleproductskuinfo,jdbcType=VARCHAR},",
          "ShowPrice = #{showprice,jdbcType=REAL},",
          "RealPrice = #{realprice,jdbcType=REAL},",
          "UpdateUserId = #{updateuserid,jdbcType=INTEGER},",
          "UpdateUserName = #{updateusername,jdbcType=VARCHAR},",
          "UpdateDate = #{updatedate,jdbcType=TIMESTAMP},",
          "CreateUserId = #{createuserid,jdbcType=INTEGER},",
          "CreateUserName = #{createusername,jdbcType=VARCHAR},",
          "CreateDate = #{createdate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationid,jdbcType=INTEGER},",
          "OrganizationName = #{organizationname,jdbcType=VARCHAR},",
          "Detail = #{detail,jdbcType=LONGVARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(ProductUnsale record);

    @Update({
        "update marketing_product_unsale",
        "set ProductId = #{productid,jdbcType=VARCHAR},",
          "UnsaleProductName = #{unsaleproductname,jdbcType=VARCHAR},",
          "UnsaleProductPic = #{unsaleproductpic,jdbcType=VARCHAR},",
          "UnsaleProductSkuNum = #{unsaleproductskunum,jdbcType=INTEGER},",
          "UnsaleProductSkuInfo = #{unsaleproductskuinfo,jdbcType=VARCHAR},",
          "ShowPrice = #{showprice,jdbcType=REAL},",
          "RealPrice = #{realprice,jdbcType=REAL},",
          "UpdateUserId = #{updateuserid,jdbcType=INTEGER},",
          "UpdateUserName = #{updateusername,jdbcType=VARCHAR},",
          "UpdateDate = #{updatedate,jdbcType=TIMESTAMP},",
          "CreateUserId = #{createuserid,jdbcType=INTEGER},",
          "CreateUserName = #{createusername,jdbcType=VARCHAR},",
          "CreateDate = #{createdate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationid,jdbcType=INTEGER},",
          "OrganizationName = #{organizationname,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ProductUnsale record);
}