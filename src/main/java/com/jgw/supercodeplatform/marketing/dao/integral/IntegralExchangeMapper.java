package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
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
        "values (#{id,jdbcType=BIGINT}, #{exchangeobject,jdbcType=BIT}, ",
        "#{exchangeresource,jdbcType=BIT}, #{exchangeintegral,jdbcType=INTEGER}, ",
        "#{exchangestock,jdbcType=INTEGER}, #{havestock,jdbcType=INTEGER}, ",
        "#{customerlimitnum,jdbcType=INTEGER}, #{status,jdbcType=BIT}, ",
        "#{payway,jdbcType=BIT}, #{undercarriagesetway,jdbcType=BIT}, ",
        "#{undercarriage,jdbcType=TIMESTAMP}, #{stockwarning,jdbcType=BIT}, ",
        "#{stockwarningnum,jdbcType=INTEGER}, #{createuserid,jdbcType=INTEGER}, ",
        "#{createusername,jdbcType=VARCHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{updateuserid,jdbcType=INTEGER}, #{updateusername,jdbcType=VARCHAR}, ",
        "#{updatedate,jdbcType=TIMESTAMP}, #{organizationid,jdbcType=INTEGER}, ",
        "#{organizationname,jdbcType=VARCHAR}, #{productid,jdbcType=VARCHAR}, ",
        "#{productname,jdbcType=VARCHAR}, #{skuname,jdbcType=VARCHAR}, ",
        "#{skuurl,jdbcType=VARCHAR}, #{skustatus,jdbcType=VARCHAR})"
    })
    int insert(IntegralExchange record);

    @InsertProvider(type=IntegralExchangeSqlProvider.class, method="insertSelective")
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
        @Result(column="ExchangeObject", property="exchangeobject", jdbcType=JdbcType.BIT),
        @Result(column="ExchangeResource", property="exchangeresource", jdbcType=JdbcType.BIT),
        @Result(column="ExchangeIntegral", property="exchangeintegral", jdbcType=JdbcType.INTEGER),
        @Result(column="ExchangeStock", property="exchangestock", jdbcType=JdbcType.INTEGER),
        @Result(column="HaveStock", property="havestock", jdbcType=JdbcType.INTEGER),
        @Result(column="CustomerLimitNum", property="customerlimitnum", jdbcType=JdbcType.INTEGER),
        @Result(column="Status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="PayWay", property="payway", jdbcType=JdbcType.BIT),
        @Result(column="UndercarriageSetWay", property="undercarriagesetway", jdbcType=JdbcType.BIT),
        @Result(column="UnderCarriage", property="undercarriage", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="StockWarning", property="stockwarning", jdbcType=JdbcType.BIT),
        @Result(column="StockWarningNum", property="stockwarningnum", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateUserId", property="createuserid", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateUserName", property="createusername", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateUserId", property="updateuserid", jdbcType=JdbcType.INTEGER),
        @Result(column="UpdateUserName", property="updateusername", jdbcType=JdbcType.VARCHAR),
        @Result(column="UpdateDate", property="updatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationid", jdbcType=JdbcType.INTEGER),
        @Result(column="OrganizationName", property="organizationname", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductId", property="productid", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productname", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuName", property="skuname", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuUrl", property="skuurl", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuStatus", property="skustatus", jdbcType=JdbcType.VARCHAR)
    })
    IntegralExchange selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralExchangeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralExchange record);

    @Update({
        "update marketing_integral_exchange",
        "set ExchangeObject = #{exchangeobject,jdbcType=BIT},",
          "ExchangeResource = #{exchangeresource,jdbcType=BIT},",
          "ExchangeIntegral = #{exchangeintegral,jdbcType=INTEGER},",
          "ExchangeStock = #{exchangestock,jdbcType=INTEGER},",
          "HaveStock = #{havestock,jdbcType=INTEGER},",
          "CustomerLimitNum = #{customerlimitnum,jdbcType=INTEGER},",
          "Status = #{status,jdbcType=BIT},",
          "PayWay = #{payway,jdbcType=BIT},",
          "UndercarriageSetWay = #{undercarriagesetway,jdbcType=BIT},",
          "UnderCarriage = #{undercarriage,jdbcType=TIMESTAMP},",
          "StockWarning = #{stockwarning,jdbcType=BIT},",
          "StockWarningNum = #{stockwarningnum,jdbcType=INTEGER},",
          "CreateUserId = #{createuserid,jdbcType=INTEGER},",
          "CreateUserName = #{createusername,jdbcType=VARCHAR},",
          "CreateDate = #{createdate,jdbcType=TIMESTAMP},",
          "UpdateUserId = #{updateuserid,jdbcType=INTEGER},",
          "UpdateUserName = #{updateusername,jdbcType=VARCHAR},",
          "UpdateDate = #{updatedate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationid,jdbcType=INTEGER},",
          "OrganizationName = #{organizationname,jdbcType=VARCHAR},",
          "ProductId = #{productid,jdbcType=VARCHAR},",
          "ProductName = #{productname,jdbcType=VARCHAR},",
          "SkuName = #{skuname,jdbcType=VARCHAR},",
          "SkuUrl = #{skuurl,jdbcType=VARCHAR},",
          "SkuStatus = #{skustatus,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralExchange record);
}