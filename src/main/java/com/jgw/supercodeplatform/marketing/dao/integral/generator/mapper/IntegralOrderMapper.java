package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralOrderSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface IntegralOrderMapper {
    @Delete({
        "delete from marketing_order",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_order (Id, OrderId, ",
        "ExchangeResource, ProductId, ",
        "ProductName, SkuName, ",
        "SkuUrl, ExchangeIntegralNum, ",
        "ExchangeNum, Name, ",
        "Mobile, Address, ",
        "Status, MemberId, ",
        "MemberName, CreateDate, ",
        "DeliveryDate, OrganizationId, ",
        "OrganizationName)",
        "values (#{id,jdbcType=BIGINT}, #{orderId,jdbcType=VARCHAR}, ",
        "#{exchangeResource,jdbcType=TINYINT}, #{productId,jdbcType=VARCHAR}, ",
        "#{productName,jdbcType=VARCHAR}, #{skuName,jdbcType=VARCHAR}, ",
        "#{skuUrl,jdbcType=VARCHAR}, #{exchangeIntegralNum,jdbcType=INTEGER}, ",
        "#{exchangeNum,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=TINYINT}, #{memberId,jdbcType=BIGINT}, ",
        "#{memberName,jdbcType=VARCHAR}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{deliveryDate,jdbcType=TIMESTAMP}, #{organizationId,jdbcType=VARCHAR}, ",
        "#{organizationName,jdbcType=VARCHAR})"
    })
    int insert(IntegralOrder record);

    @InsertProvider(type= IntegralOrderSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralOrder record);

    @Select({
        "select",
        "Id, OrderId, ExchangeResource, ProductId, ProductName, SkuName, SkuUrl, ExchangeIntegralNum, ",
        "ExchangeNum, Name, Mobile, Address, Status, MemberId, MemberName, CreateDate, ",
        "DeliveryDate, OrganizationId, OrganizationName",
        "from marketing_order",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="OrderId", property="orderId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ExchangeResource", property="exchangeResource", jdbcType=JdbcType.TINYINT),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuName", property="skuName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuUrl", property="skuUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="ExchangeIntegralNum", property="exchangeIntegralNum", jdbcType=JdbcType.INTEGER),
        @Result(column="ExchangeNum", property="exchangeNum", jdbcType=JdbcType.INTEGER),
        @Result(column="Name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="Address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="Status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="MemberId", property="memberId", jdbcType=JdbcType.BIGINT),
        @Result(column="MemberName", property="memberName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DeliveryDate", property="deliveryDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR)
    })
    IntegralOrder selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralOrderSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralOrder record);

    @Update({
        "update marketing_order",
        "set OrderId = #{orderId,jdbcType=VARCHAR},",
          "ExchangeResource = #{exchangeResource,jdbcType=TINYINT},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "ProductName = #{productName,jdbcType=VARCHAR},",
          "SkuName = #{skuName,jdbcType=VARCHAR},",
          "SkuUrl = #{skuUrl,jdbcType=VARCHAR},",
          "ExchangeIntegralNum = #{exchangeIntegralNum,jdbcType=INTEGER},",
          "ExchangeNum = #{exchangeNum,jdbcType=INTEGER},",
          "Name = #{name,jdbcType=VARCHAR},",
          "Mobile = #{mobile,jdbcType=VARCHAR},",
          "Address = #{address,jdbcType=VARCHAR},",
          "Status = #{status,jdbcType=TINYINT},",
          "MemberId = #{memberId,jdbcType=BIGINT},",
          "MemberName = #{memberName,jdbcType=VARCHAR},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "DeliveryDate = #{deliveryDate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralOrder record);
}