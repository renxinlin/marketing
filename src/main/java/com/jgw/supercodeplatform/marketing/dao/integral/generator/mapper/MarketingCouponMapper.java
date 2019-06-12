package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.MarketingCouponSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingCoupon;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface MarketingCouponMapper {
    @Delete({
        "delete from marketing_coupon",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_coupon (Id, ActivitySetId, ",
        "OrganizationId, OrganizationName, ",
        "CouponAmount, DeductionStartDate,DeductionEndDate, ",
        "DeductionProductType, DeductionChannelType, ",
        "CreateTime, UpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{activitySetId,jdbcType=BIGINT}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
        "#{couponAmount,jdbcType=DOUBLE}, #{deductionStartDate,jdbcType=DATE},#{deductionEndDate,jdbcType=DATE}, ",
        "#{deductionProductType,jdbcType=TINYINT}, #{deductionChannelType,jdbcType=TINYINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(MarketingCoupon record);

    @InsertProvider(type=MarketingCouponSqlProvider.class, method="insertSelective")
    int insertSelective(MarketingCoupon record);

    @Select({
        "select",
        "Id, ActivitySetId, OrganizationId, OrganizationName, CouponAmount, DeductionStartDate, DeductionEndDate,",
        "DeductionProductType, DeductionChannelType, CreateTime, UpdateTime",
        "from marketing_coupon",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ActivitySetId", property="activitySetId", jdbcType=JdbcType.BIGINT),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CouponAmount", property="couponAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="DeductionStartDate", property="deductionStartDate", jdbcType=JdbcType.DATE),
        @Result(column="DeductionEndDate", property="deductionEndDate", jdbcType=JdbcType.DATE),
        @Result(column="DeductionProductType", property="deductionProductType", jdbcType=JdbcType.TINYINT),
        @Result(column="DeductionChannelType", property="deductionChannelType", jdbcType=JdbcType.TINYINT),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    MarketingCoupon selectByPrimaryKey(Long id);

    @UpdateProvider(type=MarketingCouponSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MarketingCoupon record);

    @Update({
        "update marketing_coupon",
        "set ActivitySetId = #{activitySetId,jdbcType=BIGINT},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "CouponAmount = #{couponAmount,jdbcType=DOUBLE},",
          "DeductionStartDate = #{deductionStartDate,jdbcType=DATE},",
          "DeductionEndDate = #{deductionEndDate,jdbcType=DATE},",
          "DeductionProductType = #{deductionProductType,jdbcType=TINYINT},",
          "DeductionChannelType = #{deductionChannelType,jdbcType=TINYINT},",
          "CreateTime = #{createTime,jdbcType=TIMESTAMP},",
          "UpdateTime = #{updateTime,jdbcType=TIMESTAMP}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MarketingCoupon record);
}