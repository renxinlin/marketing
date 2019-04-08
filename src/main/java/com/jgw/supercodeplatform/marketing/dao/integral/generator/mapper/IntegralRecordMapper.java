package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralRecordSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface IntegralRecordMapper {
    @Delete({
        "delete from marketing_integral_record",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_record (Id, CustomerType, ",
        "CustomerId, CustomerName, ",
        "Mobile, IntegralReasonCode, ",
        "IntegralReason, ProductId, ",
        "ProductName, OuterCodeId, ",
        "CodeTypeId, RegisterStore, ",
        "RegisterStoreId, CreateDate, ",
        "OrganizationId, OrganizationName, ",
        "IntegralNum)",
        "values (#{id,jdbcType=BIGINT}, #{customertype,jdbcType=BIT}, ",
        "#{customerid,jdbcType=INTEGER}, #{customername,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{integralreasoncode,jdbcType=INTEGER}, ",
        "#{integralreason,jdbcType=VARCHAR}, #{productid,jdbcType=VARCHAR}, ",
        "#{productname,jdbcType=VARCHAR}, #{outercodeid,jdbcType=VARCHAR}, ",
        "#{codetypeid,jdbcType=VARCHAR}, #{registerstore,jdbcType=VARCHAR}, ",
        "#{registerstoreid,jdbcType=INTEGER}, #{createdate,jdbcType=TIMESTAMP}, ",
        "#{organizationid,jdbcType=INTEGER}, #{organizationname,jdbcType=VARCHAR}, ",
        "#{integralnum,jdbcType=INTEGER})"
    })
    int insert(IntegralRecord record);

    @InsertProvider(type= IntegralRecordSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRecord record);

    @Select({
        "select",
        "Id, CustomerType, CustomerId, CustomerName, Mobile, IntegralReasonCode, IntegralReason, ",
        "ProductId, ProductName, OuterCodeId, CodeTypeId, RegisterStore, RegisterStoreId, ",
        "CreateDate, OrganizationId, OrganizationName, IntegralNum",
        "from marketing_integral_record",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CustomerType", property="customertype", jdbcType=JdbcType.BIT),
        @Result(column="CustomerId", property="customerid", jdbcType=JdbcType.INTEGER),
        @Result(column="CustomerName", property="customername", jdbcType=JdbcType.VARCHAR),
        @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="IntegralReasonCode", property="integralreasoncode", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralReason", property="integralreason", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductId", property="productid", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productname", jdbcType=JdbcType.VARCHAR),
        @Result(column="OuterCodeId", property="outercodeid", jdbcType=JdbcType.VARCHAR),
        @Result(column="CodeTypeId", property="codetypeid", jdbcType=JdbcType.VARCHAR),
        @Result(column="RegisterStore", property="registerstore", jdbcType=JdbcType.VARCHAR),
        @Result(column="RegisterStoreId", property="registerstoreid", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateDate", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationid", jdbcType=JdbcType.INTEGER),
        @Result(column="OrganizationName", property="organizationname", jdbcType=JdbcType.VARCHAR),
        @Result(column="IntegralNum", property="integralnum", jdbcType=JdbcType.INTEGER)
    })
    IntegralRecord selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRecord record);

    @Update({
        "update marketing_integral_record",
        "set CustomerType = #{customertype,jdbcType=BIT},",
          "CustomerId = #{customerid,jdbcType=INTEGER},",
          "CustomerName = #{customername,jdbcType=VARCHAR},",
          "Mobile = #{mobile,jdbcType=VARCHAR},",
          "IntegralReasonCode = #{integralreasoncode,jdbcType=INTEGER},",
          "IntegralReason = #{integralreason,jdbcType=VARCHAR},",
          "ProductId = #{productid,jdbcType=VARCHAR},",
          "ProductName = #{productname,jdbcType=VARCHAR},",
          "OuterCodeId = #{outercodeid,jdbcType=VARCHAR},",
          "CodeTypeId = #{codetypeid,jdbcType=VARCHAR},",
          "RegisterStore = #{registerstore,jdbcType=VARCHAR},",
          "RegisterStoreId = #{registerstoreid,jdbcType=INTEGER},",
          "CreateDate = #{createdate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationid,jdbcType=INTEGER},",
          "OrganizationName = #{organizationname,jdbcType=VARCHAR},",
          "IntegralNum = #{integralnum,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRecord record);
}