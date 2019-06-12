package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.MarketingMemberCouponSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface MarketingMemberCouponMapper {
    @Delete({
        "delete from marketing_member_coupon",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_member_coupon (Id, MemberId, ",
        "CouponId, CouponCode, CouponAmount,",
        "MemberPhone, ProductId, ",
        "ProductBatchId, ProductBatchName, ",
        "SbatchId, ProductName, ",
        "ObtainCustomerId, DeductionStartDate, DeductionEndDate, ObtainCustmerName,",
        "CreateTime, VerifyCustomerId, ",
        "VerifyCustomerName, VerifyPersonName, ",
        "VerifyPersonPhone, VerifyTime, ",
        "VerifyPersonType, Used)",
        "values (#{id,jdbcType=BIGINT}, #{memberId,jdbcType=BIGINT}, ",
        "#{couponId,jdbcType=BIGINT}, #{couponCode,jdbcType=VARCHAR}, #{couponAmount,jdbcType=DOUBLE}, ",
        "#{memberPhone,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{productBatchId,jdbcType=VARCHAR}, #{productBatchName,jdbcType=VARCHAR}, ",
        "#{sbatchId,jdbcType=VARCHAR}, #{productName,jdbcType=VARCHAR}, ",
        "#{obtainCustomerId,jdbcType=VARCHAR}, #{deductionStartDate,jdbcType=DATE}, #{deductionEndDate,jdbcType=DATE}, ",
        " #{obtainCustmerName,jdbcType=VARCHAR},#{createTime,jdbcType=TIMESTAMP}, #{verifyCustomerId,jdbcType=VARCHAR}, ",
        "#{verifyCustomerName,jdbcType=VARCHAR}, #{verifyPersonName,jdbcType=VARCHAR}, ",
        "#{verifyPersonPhone,jdbcType=VARCHAR}, #{verifyTime,jdbcType=TIMESTAMP}, ",
        "#{verifyPersonType,jdbcType=TINYINT}, #{used,jdbcType=TINYINT})"
    })
    int insert(MarketingMemberCoupon record);

    @InsertProvider(type=MarketingMemberCouponSqlProvider.class, method="insertSelective")
    int insertSelective(MarketingMemberCoupon record);

    @Select({
        "select",
        "Id, MemberId, CouponId, CouponCode,CouponAmount, MemberPhone, ProductId, ProductBatchId, ",
        "ProductBatchName, SbatchId, ProductName, ObtainCustomerId, DeductionStartDate, DeductionEndDate, CreateTime, ObtainCustmerName,",
        "VerifyCustomerId, VerifyCustomerName, VerifyPersonName, VerifyPersonPhone, VerifyTime, ",
        "VerifyPersonType, Used",
        "from marketing_member_coupon",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="MemberId", property="memberId", jdbcType=JdbcType.BIGINT),
        @Result(column="CouponId", property="couponId", jdbcType=JdbcType.BIGINT),
        @Result(column="CouponCode", property="couponCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CouponAmount", property="couponAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="MemberPhone", property="memberPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductBatchId", property="productBatchId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductBatchName", property="productBatchName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SbatchId", property="sbatchId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ObtainCustomerId", property="obtainCustomerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ObtainCustmerName", property="obtainCustmerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="DeductionStartDate", property="deductionStartDate", jdbcType=JdbcType.DATE),
        @Result(column="DeductionEndDate", property="deductionEndDate", jdbcType=JdbcType.DATE),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="VerifyCustomerId", property="verifyCustomerId", jdbcType=JdbcType.VARCHAR),
        @Result(column="VerifyCustomerName", property="verifyCustomerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="VerifyPersonName", property="verifyPersonName", jdbcType=JdbcType.VARCHAR),
        @Result(column="VerifyPersonPhone", property="verifyPersonPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="VerifyTime", property="verifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="VerifyPersonType", property="verifyPersonType", jdbcType=JdbcType.TINYINT),
        @Result(column="Used", property="used", jdbcType=JdbcType.TINYINT)
    })
    MarketingMemberCoupon selectByPrimaryKey(Long id);

    @UpdateProvider(type=MarketingMemberCouponSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MarketingMemberCoupon record);

    @Update({
        "update marketing_member_coupon",
        "set MemberId = #{memberId,jdbcType=BIGINT},",
          "CouponId = #{couponId,jdbcType=BIGINT},",
          "CouponCode = #{couponCode,jdbcType=VARCHAR},",
          "CouponAmount = #{couponAmount,jdbcType=DOUBLE},",
          "MemberPhone = #{memberPhone,jdbcType=VARCHAR},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "ProductBatchId = #{productBatchId,jdbcType=VARCHAR},",
          "ProductBatchName = #{productBatchName,jdbcType=VARCHAR},",
          "SbatchId = #{sbatchId,jdbcType=VARCHAR},",
          "ProductName = #{productName,jdbcType=VARCHAR},",
          "ObtainCustomerId = #{obtainCustomerId,jdbcType=VARCHAR},",
          "DeductionStartDate = #{deductionStartDate,jdbcType=DATE},",
          "DeductionEndDate = #{deductionEndDate,jdbcType=DATE},",
          "ObtainCustmerName = #{obtainCustmerName,jdbcType=VARCHAR},",
          "CreateTime = #{createTime,jdbcType=TIMESTAMP},",
          "VerifyCustomerId = #{verifyCustomerId,jdbcType=VARCHAR},",
          "VerifyCustomerName = #{verifyCustomerName,jdbcType=VARCHAR},",
          "VerifyPersonName = #{verifyPersonName,jdbcType=VARCHAR},",
          "VerifyPersonPhone = #{verifyPersonPhone,jdbcType=VARCHAR},",
          "VerifyTime = #{verifyTime,jdbcType=TIMESTAMP},",
          "VerifyPersonType = #{verifyPersonType,jdbcType=TINYINT},",
          "Used = #{used,jdbcType=TINYINT}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MarketingMemberCoupon record);
}