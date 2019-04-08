package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralOrderSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
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
        "UpdateUserId, UpdateUserName, ",
        "update_date, organization_id, ",
        "organization_name)",
        "values (#{id,jdbcType=BIGINT}, #{orderid,jdbcType=VARCHAR}, ",
        "#{exchangeresource,jdbcType=BIT}, #{productid,jdbcType=VARCHAR}, ",
        "#{productname,jdbcType=VARCHAR}, #{skuname,jdbcType=VARCHAR}, ",
        "#{skuurl,jdbcType=VARCHAR}, #{exchangeintegralnum,jdbcType=INTEGER}, ",
        "#{exchangenum,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=VARCHAR}, #{memberid,jdbcType=INTEGER}, ",
        "#{membername,jdbcType=VARCHAR}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{updateuserid,jdbcType=INTEGER}, #{updateusername,jdbcType=VARCHAR}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{organizationId,jdbcType=INTEGER}, ",
        "#{organizationName,jdbcType=VARCHAR})"
    })
    int insert(IntegralOrder record);

    @InsertProvider(type= IntegralOrderSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralOrder record);

    @Select({
        "select",
        "Id, OrderId, ExchangeResource, ProductId, ProductName, SkuName, SkuUrl, ExchangeIntegralNum, ",
        "ExchangeNum, Name, Mobile, Address, Status, MemberId, MemberName, CreateDate, ",
        "UpdateUserId, UpdateUserName, update_date, organization_id, organization_name",
        "from marketing_order",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="OrderId", property="orderid", jdbcType=JdbcType.VARCHAR),
        @Result(column="ExchangeResource", property="exchangeresource", jdbcType=JdbcType.BIT),
        @Result(column="ProductId", property="productid", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productname", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuName", property="skuname", jdbcType=JdbcType.VARCHAR),
        @Result(column="SkuUrl", property="skuurl", jdbcType=JdbcType.VARCHAR),
        @Result(column="ExchangeIntegralNum", property="exchangeintegralnum", jdbcType=JdbcType.INTEGER),
        @Result(column="ExchangeNum", property="exchangenum", jdbcType=JdbcType.INTEGER),
        @Result(column="Name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="Address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="Status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="MemberId", property="memberid", jdbcType=JdbcType.INTEGER),
        @Result(column="MemberName", property="membername", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateUserId", property="updateuserid", jdbcType=JdbcType.INTEGER),
        @Result(column="UpdateUserName", property="updateusername", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_date", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.INTEGER),
        @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR)
    })
    IntegralOrder selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralOrderSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralOrder record);

    @Update({
        "update marketing_order",
        "set OrderId = #{orderid,jdbcType=VARCHAR},",
          "ExchangeResource = #{exchangeresource,jdbcType=BIT},",
          "ProductId = #{productid,jdbcType=VARCHAR},",
          "ProductName = #{productname,jdbcType=VARCHAR},",
          "SkuName = #{skuname,jdbcType=VARCHAR},",
          "SkuUrl = #{skuurl,jdbcType=VARCHAR},",
          "ExchangeIntegralNum = #{exchangeintegralnum,jdbcType=INTEGER},",
          "ExchangeNum = #{exchangenum,jdbcType=INTEGER},",
          "Name = #{name,jdbcType=VARCHAR},",
          "Mobile = #{mobile,jdbcType=VARCHAR},",
          "Address = #{address,jdbcType=VARCHAR},",
          "Status = #{status,jdbcType=VARCHAR},",
          "MemberId = #{memberid,jdbcType=INTEGER},",
          "MemberName = #{membername,jdbcType=VARCHAR},",
          "CreateDate = #{createdate,jdbcType=TIMESTAMP},",
          "UpdateUserId = #{updateuserid,jdbcType=INTEGER},",
          "UpdateUserName = #{updateusername,jdbcType=VARCHAR},",
          "update_date = #{updateDate,jdbcType=TIMESTAMP},",
          "organization_id = #{organizationId,jdbcType=INTEGER},",
          "organization_name = #{organizationName,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralOrder record);
}