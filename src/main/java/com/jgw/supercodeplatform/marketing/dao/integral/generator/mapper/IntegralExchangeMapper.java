package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralExchangeSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface IntegralExchangeMapper {
    @Delete({
        "delete from marketing_integral_exchange",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_exchange (Id, MemberType, ",
        "ExchangeResource, ExchangeIntegral, ",
        "ExchangeStock, HaveStock, ",
        "CustomerLimitNum, Status, ",
        "PayWay, UndercarriageSetWay, ",
        "UnderCarriage, StockWarning, ",
        "StockWarningNum, OrganizationId, ",
        "OrganizationName, ProductId, ",
        "ProductName, SkuName, ",
        "SkuUrl, SkuStatus, ",
        "ProductPic, ShowPrice)",
        "values (#{id,jdbcType=BIGINT}, #{memberType,jdbcType=TINYINT}, ",
        "#{exchangeResource,jdbcType=TINYINT}, #{exchangeIntegral,jdbcType=INTEGER}, ",
        "#{exchangeStock,jdbcType=INTEGER}, #{haveStock,jdbcType=INTEGER}, ",
        "#{customerLimitNum,jdbcType=INTEGER}, #{status,jdbcType=TINYINT}, ",
        "#{payWay,jdbcType=TINYINT}, #{undercarriageSetWay,jdbcType=TINYINT}, ",
        "#{underCarriage,jdbcType=TIMESTAMP}, #{stockWarning,jdbcType=TINYINT}, ",
        "#{stockWarningNum,jdbcType=INTEGER}, #{organizationId,jdbcType=VARCHAR}, ",
        "#{organizationName,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{productName,jdbcType=VARCHAR}, #{skuName,jdbcType=VARCHAR}, ",
        "#{skuUrl,jdbcType=VARCHAR}, #{skuStatus,jdbcType=TINYINT}, ",
        "#{productPic,jdbcType=VARCHAR}, #{showPrice,jdbcType=REAL})"
    })
    int insert(IntegralExchange record);

    @InsertProvider(type= IntegralExchangeSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralExchange record);

    @Select({
        "select",
        "Id, MemberType, ExchangeResource, ExchangeIntegral, ExchangeStock, HaveStock, ",
        "CustomerLimitNum, Status, PayWay, UndercarriageSetWay, UnderCarriage, StockWarning, ",
        "StockWarningNum, OrganizationId, OrganizationName, ProductId, ProductName, SkuName, ",
        "SkuUrl, SkuStatus, ProductPic, ShowPrice",
        "from marketing_integral_exchange",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="MemberType", property="memberType", jdbcType=JdbcType.TINYINT),
        @Result(column="ExchangeResource", property="exchangeResource", jdbcType=JdbcType.TINYINT),
        @Result(column="ExchangeIntegral", property="exchangeIntegral", jdbcType=JdbcType.INTEGER),
        @Result(column="ExchangeStock", property="exchangeStock", jdbcType=JdbcType.INTEGER),
        @Result(column="HaveStock", property="haveStock", jdbcType=JdbcType.INTEGER),
        @Result(column="CustomerLimitNum", property="customerLimitNum", jdbcType=JdbcType.INTEGER),
        @Result(column="Status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="PayWay", property="payWay", jdbcType=JdbcType.TINYINT),
        @Result(column="UndercarriageSetWay", property="undercarriageSetWay", jdbcType=JdbcType.TINYINT),
        @Result(column="UnderCarriage", property="underCarriage", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="StockWarning", property="stockWarning", jdbcType=JdbcType.TINYINT),
        @Result(column="StockWarningNum", property="stockWarningNum", jdbcType=JdbcType.INTEGER),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuName", property="skuName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuUrl", property="skuUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuStatus", property="skuStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="ProductPic", property="productPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="ShowPrice", property="showPrice", jdbcType=JdbcType.REAL),
        @Result(column="ShowPrice", property="showPriceStr", jdbcType=JdbcType.REAL)
    })
    IntegralExchange selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralExchangeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralExchange record);

    @Update({
        "update marketing_integral_exchange",
        "set MemberType = #{memberType,jdbcType=TINYINT},",
          "ExchangeResource = #{exchangeResource,jdbcType=TINYINT},",
          "ExchangeIntegral = #{exchangeIntegral,jdbcType=INTEGER},",
          "ExchangeStock = #{exchangeStock,jdbcType=INTEGER},",
          "HaveStock = #{haveStock,jdbcType=INTEGER},",
          "CustomerLimitNum = #{customerLimitNum,jdbcType=INTEGER},",
          "Status = #{status,jdbcType=TINYINT},",
          "PayWay = #{payWay,jdbcType=TINYINT},",
          "UndercarriageSetWay = #{undercarriageSetWay,jdbcType=TINYINT},",
          "UnderCarriage = #{underCarriage,jdbcType=TIMESTAMP},",
          "StockWarning = #{stockWarning,jdbcType=TINYINT},",
          "StockWarningNum = #{stockWarningNum,jdbcType=INTEGER},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "ProductName = #{productName,jdbcType=VARCHAR},",
          "SkuName = #{skuName,jdbcType=VARCHAR},",
          "SkuUrl = #{skuUrl,jdbcType=VARCHAR},",
          "SkuStatus = #{skuStatus,jdbcType=TINYINT},",
          "ProductPic = #{productPic,jdbcType=VARCHAR},",
          "ShowPrice = #{showPrice,jdbcType=REAL}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralExchange record);
}