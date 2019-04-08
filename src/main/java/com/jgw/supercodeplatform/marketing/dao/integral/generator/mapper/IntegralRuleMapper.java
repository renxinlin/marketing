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
        "OrganizationId, OrganizationName, ",
        "IsEffective)",
        "values (#{id,jdbcType=BIGINT}, #{timeLimitStatus,jdbcType=TINYINT}, ",
        "#{timeLimitDate,jdbcType=TIMESTAMP}, #{integralLimitStatus,jdbcType=TINYINT}, ",
        "#{integralLimit,jdbcType=INTEGER}, #{integralLimitAge,jdbcType=TINYINT}, ",
        "#{integralByRegister,jdbcType=INTEGER}, #{integralByBirthday,jdbcType=INTEGER}, ",
        "#{integralByFirstTime,jdbcType=INTEGER}, #{integralByRegisterStatus,jdbcType=TINYINT}, ",
        "#{integralByBirthdayStatus,jdbcType=TINYINT}, #{integralByFirstTimeStatus,jdbcType=TINYINT}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
        "#{isEffective,jdbcType=TINYINT})"
    })
    int insert(IntegralRule record);

    @InsertProvider(type= IntegralRuleSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRule record);

    @Select({
        "select",
        "Id, TimeLimitStatus, TimeLimitDate, IntegralLimitStatus, IntegralLimit, IntegralLimitAge, ",
        "IntegralByRegister, IntegralByBirthday, IntegralByFirstTime, IntegralByRegisterStatus, ",
        "IntegralByBirthdayStatus, IntegralByFirstTimeStatus, OrganizationId, OrganizationName, ",
        "IsEffective",
        "from marketing_integral_rule",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TimeLimitStatus", property="timeLimitStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="TimeLimitDate", property="timeLimitDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IntegralLimitStatus", property="integralLimitStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="IntegralLimit", property="integralLimit", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralLimitAge", property="integralLimitAge", jdbcType=JdbcType.TINYINT),
        @Result(column="IntegralByRegister", property="integralByRegister", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByBirthday", property="integralByBirthday", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByFirstTime", property="integralByFirstTime", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByRegisterStatus", property="integralByRegisterStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="IntegralByBirthdayStatus", property="integralByBirthdayStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="IntegralByFirstTimeStatus", property="integralByFirstTimeStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="IsEffective", property="isEffective", jdbcType=JdbcType.TINYINT)
    })
    IntegralRule selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRuleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRule record);

    @Update({
        "update marketing_integral_rule",
        "set TimeLimitStatus = #{timeLimitStatus,jdbcType=TINYINT},",
          "TimeLimitDate = #{timeLimitDate,jdbcType=TIMESTAMP},",
          "IntegralLimitStatus = #{integralLimitStatus,jdbcType=TINYINT},",
          "IntegralLimit = #{integralLimit,jdbcType=INTEGER},",
          "IntegralLimitAge = #{integralLimitAge,jdbcType=TINYINT},",
          "IntegralByRegister = #{integralByRegister,jdbcType=INTEGER},",
          "IntegralByBirthday = #{integralByBirthday,jdbcType=INTEGER},",
          "IntegralByFirstTime = #{integralByFirstTime,jdbcType=INTEGER},",
          "IntegralByRegisterStatus = #{integralByRegisterStatus,jdbcType=TINYINT},",
          "IntegralByBirthdayStatus = #{integralByBirthdayStatus,jdbcType=TINYINT},",
          "IntegralByFirstTimeStatus = #{integralByFirstTimeStatus,jdbcType=TINYINT},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "IsEffective = #{isEffective,jdbcType=TINYINT}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRule record);
}