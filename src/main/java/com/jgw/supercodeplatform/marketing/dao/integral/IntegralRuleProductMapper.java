package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface IntegralRuleProductMapper {
    @Delete({
        "delete from marketing_integral_rule_product",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_rule_product (Id, IntegralRuleId, ",
        "ProductId, ProductName, ",
        "ProductPrice, RewardObject, ",
        "RewardRule, PerConsume, ",
        "RewardIntegral)",
        "values (#{id,jdbcType=BIGINT}, #{integralruleid,jdbcType=INTEGER}, ",
        "#{productid,jdbcType=VARCHAR}, #{productname,jdbcType=VARCHAR}, ",
        "#{productprice,jdbcType=REAL}, #{rewardobject,jdbcType=BIT}, ",
        "#{rewardrule,jdbcType=BIT}, #{perconsume,jdbcType=REAL}, ",
        "#{rewardintegral,jdbcType=INTEGER})"
    })
    int insert(IntegralRuleProduct record);

    @InsertProvider(type=IntegralRuleProductSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRuleProduct record);

    @Select({
        "select",
        "Id, IntegralRuleId, ProductId, ProductName, ProductPrice, RewardObject, RewardRule, ",
        "PerConsume, RewardIntegral",
        "from marketing_integral_rule_product",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="IntegralRuleId", property="integralruleid", jdbcType=JdbcType.INTEGER),
        @Result(column="ProductId", property="productid", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productname", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductPrice", property="productprice", jdbcType=JdbcType.REAL),
        @Result(column="RewardObject", property="rewardobject", jdbcType=JdbcType.BIT),
        @Result(column="RewardRule", property="rewardrule", jdbcType=JdbcType.BIT),
        @Result(column="PerConsume", property="perconsume", jdbcType=JdbcType.REAL),
        @Result(column="RewardIntegral", property="rewardintegral", jdbcType=JdbcType.INTEGER)
    })
    IntegralRuleProduct selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRuleProductSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRuleProduct record);

    @Update({
        "update marketing_integral_rule_product",
        "set IntegralRuleId = #{integralruleid,jdbcType=INTEGER},",
          "ProductId = #{productid,jdbcType=VARCHAR},",
          "ProductName = #{productname,jdbcType=VARCHAR},",
          "ProductPrice = #{productprice,jdbcType=REAL},",
          "RewardObject = #{rewardobject,jdbcType=BIT},",
          "RewardRule = #{rewardrule,jdbcType=BIT},",
          "PerConsume = #{perconsume,jdbcType=REAL},",
          "RewardIntegral = #{rewardintegral,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRuleProduct record);
}