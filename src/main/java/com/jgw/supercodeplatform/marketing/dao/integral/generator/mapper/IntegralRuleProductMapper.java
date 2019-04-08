package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralRuleProductSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface IntegralRuleProductMapper {
    @Delete({
        "delete from marketing_integral_rule_product",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_rule_product (Id, IntegralRuleId, ",
        "ProductId, ProductName, ",
        "ProductPrice, MemberType, ",
        "RewardRule, PerConsume, ",
        "RewardIntegral)",
        "values (#{id,jdbcType=BIGINT}, #{integralRuleId,jdbcType=INTEGER}, ",
        "#{productId,jdbcType=VARCHAR}, #{productName,jdbcType=VARCHAR}, ",
        "#{productPrice,jdbcType=REAL}, #{memberType,jdbcType=TINYINT}, ",
        "#{rewardRule,jdbcType=TINYINT}, #{perConsume,jdbcType=REAL}, ",
        "#{rewardIntegral,jdbcType=INTEGER})"
    })
    int insert(IntegralRuleProduct record);

    @InsertProvider(type= IntegralRuleProductSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRuleProduct record);

    @Select({
        "select",
        "Id, IntegralRuleId, ProductId, ProductName, ProductPrice, MemberType, RewardRule, ",
        "PerConsume, RewardIntegral",
        "from marketing_integral_rule_product",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="IntegralRuleId", property="integralRuleId", jdbcType=JdbcType.INTEGER),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductPrice", property="productPrice", jdbcType=JdbcType.REAL),
        @Result(column="MemberType", property="memberType", jdbcType=JdbcType.TINYINT),
        @Result(column="RewardRule", property="rewardRule", jdbcType=JdbcType.TINYINT),
        @Result(column="PerConsume", property="perConsume", jdbcType=JdbcType.REAL),
        @Result(column="RewardIntegral", property="rewardIntegral", jdbcType=JdbcType.INTEGER)
    })
    IntegralRuleProduct selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRuleProductSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRuleProduct record);

    @Update({
        "update marketing_integral_rule_product",
        "set IntegralRuleId = #{integralRuleId,jdbcType=INTEGER},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "ProductName = #{productName,jdbcType=VARCHAR},",
          "ProductPrice = #{productPrice,jdbcType=REAL},",
          "MemberType = #{memberType,jdbcType=TINYINT},",
          "RewardRule = #{rewardRule,jdbcType=TINYINT},",
          "PerConsume = #{perConsume,jdbcType=REAL},",
          "RewardIntegral = #{rewardIntegral,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRuleProduct record);
}