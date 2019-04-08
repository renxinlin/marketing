package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralRuleSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface IntegralRuleMapper {
    @Delete({
        "delete from marketing_integral_rule",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_rule (Id, TimeLimitStatus, ",
        "TimeLimitDate, IntegralLimitStatus, ",
        "IntegralLimit, IntegralLimitAge, ",
        "IntegralByRegister, IntegralByBirthday, ",
        "IntegralByFirstTime, IntegralByRegisterStatus, ",
        "IntegralByBirthdayStatus, IntegralByFirstTimeStatus, ",
        "OrganizationId, OrganizationName)",
        "values (#{id,jdbcType=BIGINT}, #{timeLimitStatus,jdbcType=BIT}, ",
        "#{timeLimitDate,jdbcType=TIMESTAMP}, #{integralLimitStatus,jdbcType=BIT}, ",
        "#{integralLimit,jdbcType=INTEGER}, #{integralLimitAge,jdbcType=BIT}, ",
        "#{integralByRegister,jdbcType=INTEGER}, #{integralByBirthday,jdbcType=INTEGER}, ",
        "#{integralByFirstTime,jdbcType=INTEGER}, #{integralByRegisterStatus,jdbcType=BIT}, ",
        "#{integralByBirthdayStatus,jdbcType=BIT}, #{integralByFirstTimeStatus,jdbcType=BIT}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR})"
    })
    int insert(IntegralRule record);

    @InsertProvider(type=IntegralRuleSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRule record);

    @Select({
        "select",
        "Id, TimeLimitStatus, TimeLimitDate, IntegralLimitStatus, IntegralLimit, IntegralLimitAge, ",
        "IntegralByRegister, IntegralByBirthday, IntegralByFirstTime, IntegralByRegisterStatus, ",
        "IntegralByBirthdayStatus, IntegralByFirstTimeStatus, OrganizationId, OrganizationName",
        "from marketing_integral_rule",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TimeLimitStatus", property="timeLimitStatus", jdbcType=JdbcType.BIT),
        @Result(column="TimeLimitDate", property="timeLimitDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IntegralLimitStatus", property="integralLimitStatus", jdbcType=JdbcType.BIT),
        @Result(column="IntegralLimit", property="integralLimit", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralLimitAge", property="integralLimitAge", jdbcType=JdbcType.BIT),
        @Result(column="IntegralByRegister", property="integralByRegister", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByBirthday", property="integralByBirthday", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByFirstTime", property="integralByFirstTime", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByRegisterStatus", property="integralByRegisterStatus", jdbcType=JdbcType.BIT),
        @Result(column="IntegralByBirthdayStatus", property="integralByBirthdayStatus", jdbcType=JdbcType.BIT),
        @Result(column="IntegralByFirstTimeStatus", property="integralByFirstTimeStatus", jdbcType=JdbcType.BIT),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR)
    })
    IntegralRule selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRuleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRule record);

    @Update({
        "update marketing_integral_rule",
        "set TimeLimitStatus = #{timeLimitStatus,jdbcType=BIT},",
          "TimeLimitDate = #{timeLimitDate,jdbcType=TIMESTAMP},",
          "IntegralLimitStatus = #{integralLimitStatus,jdbcType=BIT},",
          "IntegralLimit = #{integralLimit,jdbcType=INTEGER},",
          "IntegralLimitAge = #{integralLimitAge,jdbcType=BIT},",
          "IntegralByRegister = #{integralByRegister,jdbcType=INTEGER},",
          "IntegralByBirthday = #{integralByBirthday,jdbcType=INTEGER},",
          "IntegralByFirstTime = #{integralByFirstTime,jdbcType=INTEGER},",
          "IntegralByRegisterStatus = #{integralByRegisterStatus,jdbcType=BIT},",
          "IntegralByBirthdayStatus = #{integralByBirthdayStatus,jdbcType=BIT},",
          "IntegralByFirstTimeStatus = #{integralByFirstTimeStatus,jdbcType=BIT},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRule record);
}