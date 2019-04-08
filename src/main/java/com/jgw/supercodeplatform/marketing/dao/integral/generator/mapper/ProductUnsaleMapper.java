package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.ProductUnsaleSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

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
        "values (#{id,jdbcType=BIGINT}, #{productId,jdbcType=VARCHAR}, ",
        "#{unsaleProductName,jdbcType=VARCHAR}, #{unsaleProductPic,jdbcType=VARCHAR}, ",
        "#{unsaleProductSkuNum,jdbcType=INTEGER}, #{unsaleProductSkuInfo,jdbcType=VARCHAR}, ",
        "#{showPrice,jdbcType=REAL}, #{realPrice,jdbcType=REAL}, ",
        "#{updateUserId,jdbcType=VARCHAR}, #{updateUserName,jdbcType=VARCHAR}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=VARCHAR}, ",
        "#{createUserName,jdbcType=VARCHAR}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
        "#{detail,jdbcType=LONGVARCHAR})"
    })
    int insert(ProductUnsale record);

    @InsertProvider(type= ProductUnsaleSqlProvider.class, method="insertSelective")
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
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="UnsaleProductName", property="unsaleProductName", jdbcType=JdbcType.VARCHAR),
        @Result(column="UnsaleProductPic", property="unsaleProductPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="UnsaleProductSkuNum", property="unsaleProductSkuNum", jdbcType=JdbcType.INTEGER),
        @Result(column="UnsaleProductSkuInfo", property="unsaleProductSkuInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="ShowPrice", property="showPrice", jdbcType=JdbcType.REAL),
        @Result(column="RealPrice", property="realPrice", jdbcType=JdbcType.REAL),
        @Result(column="UpdateUserId", property="updateUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="UpdateUserName", property="updateUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="UpdateDate", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CreateUserId", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateUserName", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Detail", property="detail", jdbcType=JdbcType.LONGVARCHAR)
    })
    ProductUnsale selectByPrimaryKey(Long id);

    @UpdateProvider(type=ProductUnsaleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ProductUnsale record);

    @Update({
        "update marketing_product_unsale",
        "set ProductId = #{productId,jdbcType=VARCHAR},",
          "UnsaleProductName = #{unsaleProductName,jdbcType=VARCHAR},",
          "UnsaleProductPic = #{unsaleProductPic,jdbcType=VARCHAR},",
          "UnsaleProductSkuNum = #{unsaleProductSkuNum,jdbcType=INTEGER},",
          "UnsaleProductSkuInfo = #{unsaleProductSkuInfo,jdbcType=VARCHAR},",
          "ShowPrice = #{showPrice,jdbcType=REAL},",
          "RealPrice = #{realPrice,jdbcType=REAL},",
          "UpdateUserId = #{updateUserId,jdbcType=VARCHAR},",
          "UpdateUserName = #{updateUserName,jdbcType=VARCHAR},",
          "UpdateDate = #{updateDate,jdbcType=TIMESTAMP},",
          "CreateUserId = #{createUserId,jdbcType=VARCHAR},",
          "CreateUserName = #{createUserName,jdbcType=VARCHAR},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "Detail = #{detail,jdbcType=LONGVARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(ProductUnsale record);

    @Update({
        "update marketing_product_unsale",
        "set ProductId = #{productId,jdbcType=VARCHAR},",
          "UnsaleProductName = #{unsaleProductName,jdbcType=VARCHAR},",
          "UnsaleProductPic = #{unsaleProductPic,jdbcType=VARCHAR},",
          "UnsaleProductSkuNum = #{unsaleProductSkuNum,jdbcType=INTEGER},",
          "UnsaleProductSkuInfo = #{unsaleProductSkuInfo,jdbcType=VARCHAR},",
          "ShowPrice = #{showPrice,jdbcType=REAL},",
          "RealPrice = #{realPrice,jdbcType=REAL},",
          "UpdateUserId = #{updateUserId,jdbcType=VARCHAR},",
          "UpdateUserName = #{updateUserName,jdbcType=VARCHAR},",
          "UpdateDate = #{updateDate,jdbcType=TIMESTAMP},",
          "CreateUserId = #{createUserId,jdbcType=VARCHAR},",
          "CreateUserName = #{createUserName,jdbcType=VARCHAR},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ProductUnsale record);
}