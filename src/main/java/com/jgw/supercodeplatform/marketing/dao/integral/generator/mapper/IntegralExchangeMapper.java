package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralExchangeSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface IntegralExchangeMapper {
    @Delete({
        "delete from marketing_integral_exchange",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_exchange (Id, ExchangeObject, ",
        "ExchangeResource, ExchangeIntegral, ",
        "ExchangeStock, HaveStock, ",
        "CustomerLimitNum, Status, ",
        "PayWay, UndercarriageSetWay, ",
        "UnderCarriage, StockWarning, ",
        "StockWarningNum, CreateUserId, ",
        "CreateUserName, CreateDate, ",
        "UpdateUserId, UpdateUserName, ",
        "UpdateDate, OrganizationId, ",
        "OrganizationName, ProductId, ",
        "ProductName, SkuName, ",
        "SkuUrl, SkuStatus)",
        "values (#{id,jdbcType=BIGINT}, #{exchangeObject,jdbcType=BIT}, ",
        "#{exchangeResource,jdbcType=BIT}, #{exchangeIntegral,jdbcType=INTEGER}, ",
        "#{exchangeStock,jdbcType=INTEGER}, #{haveStock,jdbcType=INTEGER}, ",
        "#{customerLimitNum,jdbcType=INTEGER}, #{status,jdbcType=BIT}, ",
        "#{payWay,jdbcType=BIT}, #{undercarriageSetWay,jdbcType=BIT}, ",
        "#{underCarriage,jdbcType=TIMESTAMP}, #{stockWarning,jdbcType=BIT}, ",
        "#{stockWarningNum,jdbcType=INTEGER}, #{createUserId,jdbcType=INTEGER}, ",
        "#{createUserName,jdbcType=VARCHAR}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{updateUserId,jdbcType=INTEGER}, #{updateUserName,jdbcType=VARCHAR}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{organizationId,jdbcType=INTEGER}, ",
        "#{organizationName,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{productName,jdbcType=VARCHAR}, #{skuName,jdbcType=VARCHAR}, ",
        "#{skuUrl,jdbcType=VARCHAR}, #{skuStatus,jdbcType=VARCHAR})"
    })
    int insert(IntegralExchange record);

    @InsertProvider(type= IntegralExchangeSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralExchange record);

    @Select({
        "select",
        "Id, ExchangeObject, ExchangeResource, ExchangeIntegral, ExchangeStock, HaveStock, ",
        "CustomerLimitNum, Status, PayWay, UndercarriageSetWay, UnderCarriage, StockWarning, ",
        "StockWarningNum, CreateUserId, CreateUserName, CreateDate, UpdateUserId, UpdateUserName, ",
        "UpdateDate, OrganizationId, OrganizationName, ProductId, ProductName, SkuName, ",
        "SkuUrl, SkuStatus",
        "from marketing_integral_exchange",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ExchangeObject", property="exchangeObject", jdbcType=JdbcType.BIT),
        @Result(column="ExchangeResource", property="exchangeResource", jdbcType=JdbcType.BIT),
        @Result(column="ExchangeIntegral", property="exchangeIntegral", jdbcType=JdbcType.INTEGER),
        @Result(column="ExchangeStock", property="exchangeStock", jdbcType=JdbcType.INTEGER),
        @Result(column="HaveStock", property="haveStock", jdbcType=JdbcType.INTEGER),
        @Result(column="CustomerLimitNum", property="customerLimitNum", jdbcType=JdbcType.INTEGER),
        @Result(column="Status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="PayWay", property="payWay", jdbcType=JdbcType.BIT),
        @Result(column="UndercarriageSetWay", property="undercarriageSetWay", jdbcType=JdbcType.BIT),
        @Result(column="UnderCarriage", property="underCarriage", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="StockWarning", property="stockWarning", jdbcType=JdbcType.BIT),
        @Result(column="StockWarningNum", property="stockWarningNum", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateUserId", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateUserName", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateUserId", property="updateUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="UpdateUserName", property="updateUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="UpdateDate", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.INTEGER),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuName", property="skuName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuUrl", property="skuUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuStatus", property="skuStatus", jdbcType=JdbcType.VARCHAR)
    })
    IntegralExchange selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralExchangeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralExchange record);

    @Update({
        "update marketing_integral_exchange",
        "set ExchangeObject = #{exchangeObject,jdbcType=BIT},",
          "ExchangeResource = #{exchangeResource,jdbcType=BIT},",
          "ExchangeIntegral = #{exchangeIntegral,jdbcType=INTEGER},",
          "ExchangeStock = #{exchangeStock,jdbcType=INTEGER},",
          "HaveStock = #{haveStock,jdbcType=INTEGER},",
          "CustomerLimitNum = #{customerLimitNum,jdbcType=INTEGER},",
          "Status = #{status,jdbcType=BIT},",
          "PayWay = #{payWay,jdbcType=BIT},",
          "UndercarriageSetWay = #{undercarriageSetWay,jdbcType=BIT},",
          "UnderCarriage = #{underCarriage,jdbcType=TIMESTAMP},",
          "StockWarning = #{stockWarning,jdbcType=BIT},",
          "StockWarningNum = #{stockWarningNum,jdbcType=INTEGER},",
          "CreateUserId = #{createUserId,jdbcType=INTEGER},",
          "CreateUserName = #{createUserName,jdbcType=VARCHAR},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "UpdateUserId = #{updateUserId,jdbcType=INTEGER},",
          "UpdateUserName = #{updateUserName,jdbcType=VARCHAR},",
          "UpdateDate = #{updateDate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationId,jdbcType=INTEGER},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "ProductName = #{productName,jdbcType=VARCHAR},",
          "SkuName = #{skuName,jdbcType=VARCHAR},",
          "SkuUrl = #{skuUrl,jdbcType=VARCHAR},",
          "SkuStatus = #{skuStatus,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralExchange record);
}