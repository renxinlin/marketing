package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralRecordSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

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
        "values (#{id,jdbcType=BIGINT}, #{customerType,jdbcType=BIT}, ",
        "#{customerId,jdbcType=INTEGER}, #{customerName,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{integralReasonCode,jdbcType=INTEGER}, ",
        "#{integralReason,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{productName,jdbcType=VARCHAR}, #{outerCodeId,jdbcType=VARCHAR}, ",
        "#{codeTypeId,jdbcType=VARCHAR}, #{registerStore,jdbcType=VARCHAR}, ",
        "#{registerStoreId,jdbcType=INTEGER}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
        "#{integralNum,jdbcType=INTEGER})"
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
        @Result(column="CustomerType", property="customerType", jdbcType=JdbcType.BIT),
        @Result(column="CustomerId", property="customerId", jdbcType=JdbcType.INTEGER),
        @Result(column="CustomerName", property="customerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="IntegralReasonCode", property="integralReasonCode", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralReason", property="integralReason", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="OuterCodeId", property="outerCodeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CodeTypeId", property="codeTypeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="RegisterStore", property="registerStore", jdbcType=JdbcType.VARCHAR),
        @Result(column="RegisterStoreId", property="registerStoreId", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="IntegralNum", property="integralNum", jdbcType=JdbcType.INTEGER)
    })
    IntegralRecord selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRecord record);

    @Update({
        "update marketing_integral_record",
        "set CustomerType = #{customerType,jdbcType=BIT},",
          "CustomerId = #{customerId,jdbcType=INTEGER},",
          "CustomerName = #{customerName,jdbcType=VARCHAR},",
          "Mobile = #{mobile,jdbcType=VARCHAR},",
          "IntegralReasonCode = #{integralReasonCode,jdbcType=INTEGER},",
          "IntegralReason = #{integralReason,jdbcType=VARCHAR},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "ProductName = #{productName,jdbcType=VARCHAR},",
          "OuterCodeId = #{outerCodeId,jdbcType=VARCHAR},",
          "CodeTypeId = #{codeTypeId,jdbcType=VARCHAR},",
          "RegisterStore = #{registerStore,jdbcType=VARCHAR},",
          "RegisterStoreId = #{registerStoreId,jdbcType=INTEGER},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "IntegralNum = #{integralNum,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRecord record);
}