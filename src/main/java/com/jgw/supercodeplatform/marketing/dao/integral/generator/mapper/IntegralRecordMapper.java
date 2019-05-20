package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralRecordSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface IntegralRecordMapper {
    @Delete({
            "delete from marketing_integral_record",
            "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into marketing_integral_record (Id, MemberType, ",
            "MemberId, MemberName, ",
            "Mobile, IntegralReasonCode, ",
            "IntegralReason, ProductId, ",
            "ProductName, OuterCodeId, ",
            "CodeTypeId, CustomerName, ",
            "CustomerId, CreateDate, ",
            "OrganizationId, OrganizationName, ",
            "IntegralNum, ProductPrice, ",
            "SalerName, SalerId, ",
            "SalerMobile, Status, ",
            "SalerAmount)",
            "values (#{id,jdbcType=BIGINT}, #{memberType,jdbcType=TINYINT}, ",
            "#{memberId,jdbcType=BIGINT}, #{memberName,jdbcType=VARCHAR}, ",
            "#{mobile,jdbcType=VARCHAR}, #{integralReasonCode,jdbcType=INTEGER}, ",
            "#{integralReason,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
            "#{productName,jdbcType=VARCHAR}, #{outerCodeId,jdbcType=VARCHAR}, ",
            "#{codeTypeId,jdbcType=VARCHAR}, #{customerName,jdbcType=VARCHAR}, ",
            "#{customerId,jdbcType=VARCHAR}, #{createDate,jdbcType=TIMESTAMP}, ",
            "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
            "#{integralNum,jdbcType=INTEGER}, #{productPrice,jdbcType=REAL}, ",
            "#{salerName,jdbcType=VARCHAR}, #{salerId,jdbcType=BIGINT}, ",
            "#{salerMobile,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR}, ",
            "#{salerAmount,jdbcType=REAL})"
    })
    int insert(IntegralRecord record);

    @InsertProvider(type=IntegralRecordSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRecord record);

    @Select({
            "select",
            "Id, MemberType, MemberId, MemberName, Mobile, IntegralReasonCode, IntegralReason, ",
            "ProductId, ProductName, OuterCodeId, CodeTypeId, CustomerName, CustomerId, CreateDate, ",
            "OrganizationId, OrganizationName, IntegralNum, ProductPrice, SalerName, SalerId, ",
            "SalerMobile, Status, SalerAmount",
            "from marketing_integral_record",
            "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="MemberType", property="memberType", jdbcType=JdbcType.TINYINT),
            @Result(column="MemberId", property="memberId", jdbcType=JdbcType.BIGINT),
            @Result(column="MemberName", property="memberName", jdbcType=JdbcType.VARCHAR),
            @Result(column="Mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
            @Result(column="IntegralReasonCode", property="integralReasonCode", jdbcType=JdbcType.INTEGER),
            @Result(column="IntegralReason", property="integralReason", jdbcType=JdbcType.VARCHAR),
            @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
            @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
            @Result(column="OuterCodeId", property="outerCodeId", jdbcType=JdbcType.VARCHAR),
            @Result(column="CodeTypeId", property="codeTypeId", jdbcType=JdbcType.VARCHAR),
            @Result(column="CustomerName", property="customerName", jdbcType=JdbcType.VARCHAR),
            @Result(column="CustomerId", property="customerId", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
            @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
            @Result(column="IntegralNum", property="integralNum", jdbcType=JdbcType.INTEGER),
            @Result(column="ProductPrice", property="productPrice", jdbcType=JdbcType.REAL),
            @Result(column="SalerName", property="salerName", jdbcType=JdbcType.VARCHAR),
            @Result(column="SalerId", property="salerId", jdbcType=JdbcType.BIGINT),
            @Result(column="SalerMobile", property="salerMobile", jdbcType=JdbcType.VARCHAR),
            @Result(column="Status", property="status", jdbcType=JdbcType.VARCHAR),
            @Result(column="SalerAmount", property="salerAmount", jdbcType=JdbcType.REAL)
    })
    IntegralRecord selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRecord record);

    @Update({
            "update marketing_integral_record",
            "set MemberType = #{memberType,jdbcType=TINYINT},",
            "MemberId = #{memberId,jdbcType=BIGINT},",
            "MemberName = #{memberName,jdbcType=VARCHAR},",
            "Mobile = #{mobile,jdbcType=VARCHAR},",
            "IntegralReasonCode = #{integralReasonCode,jdbcType=INTEGER},",
            "IntegralReason = #{integralReason,jdbcType=VARCHAR},",
            "ProductId = #{productId,jdbcType=VARCHAR},",
            "ProductName = #{productName,jdbcType=VARCHAR},",
            "OuterCodeId = #{outerCodeId,jdbcType=VARCHAR},",
            "CodeTypeId = #{codeTypeId,jdbcType=VARCHAR},",
            "CustomerName = #{customerName,jdbcType=VARCHAR},",
            "CustomerId = #{customerId,jdbcType=VARCHAR},",
            "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
            "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
            "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
            "IntegralNum = #{integralNum,jdbcType=INTEGER},",
            "ProductPrice = #{productPrice,jdbcType=REAL},",
            "SalerName = #{salerName,jdbcType=VARCHAR},",
            "SalerId = #{salerId,jdbcType=BIGINT},",
            "SalerMobile = #{salerMobile,jdbcType=VARCHAR},",
            "Status = #{status,jdbcType=VARCHAR},",
            "SalerAmount = #{salerAmount,jdbcType=REAL}",
            "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRecord record);
}