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
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_rule (id, TimeLimitStatus, ",
        "TimeLimit, IntegralLimitStatus, ",
        "IntegralLimit, IntegralLimitAge, ",
        "IntegralByRegister, IntegralByBirthday, ",
        "IntegralByFirstTime, IntegralByRegisterStatus, ",
        "IntegralByBirthdayStatus, IntegralByFirstTimeStatus, ",
        "CreateUserId, CreateUserName, ",
        "CreateDate, UpdateUserId, ",
        "UpdateUserName, UpdateUserDate, ",
        "OrganizationId, OrganizationName, ",
        "IsEffective)",
        "values (#{id,jdbcType=BIGINT}, #{timeLimitStatus,jdbcType=BIT}, ",
        "#{timeLimit,jdbcType=TIMESTAMP}, #{integralLimitStatus,jdbcType=BIT}, ",
        "#{integralLimit,jdbcType=INTEGER}, #{integralLimitAge,jdbcType=BIT}, ",
        "#{integralByRegister,jdbcType=INTEGER}, #{integralByBirthday,jdbcType=INTEGER}, ",
        "#{integralByFirstTime,jdbcType=INTEGER}, #{integralByRegisterStatus,jdbcType=BIT}, ",
        "#{integralByBirthdayStatus,jdbcType=BIT}, #{integralByFirstTimeStatus,jdbcType=BIT}, ",
        "#{createUserId,jdbcType=INTEGER}, #{createUserName,jdbcType=VARCHAR}, ",
        "#{createDate,jdbcType=TIMESTAMP}, #{updateUserId,jdbcType=INTEGER}, ",
        "#{updateUserName,jdbcType=VARCHAR}, #{updateUserDate,jdbcType=TIMESTAMP}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
        "#{isEffective,jdbcType=TINYINT})"
    })
    int insert(IntegralRule record);

    @InsertProvider(type= IntegralRuleSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRule record);

    @Select({
        "select",
        "id, TimeLimitStatus, TimeLimit, IntegralLimitStatus, IntegralLimit, IntegralLimitAge, ",
        "IntegralByRegister, IntegralByBirthday, IntegralByFirstTime, IntegralByRegisterStatus, ",
        "IntegralByBirthdayStatus, IntegralByFirstTimeStatus, CreateUserId, CreateUserName, ",
        "CreateDate, UpdateUserId, UpdateUserName, UpdateUserDate, OrganizationId, OrganizationName, ",
        "IsEffective",
        "from marketing_integral_rule",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TimeLimitStatus", property="timeLimitStatus", jdbcType=JdbcType.BIT),
        @Result(column="TimeLimit", property="timeLimit", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IntegralLimitStatus", property="integralLimitStatus", jdbcType=JdbcType.BIT),
        @Result(column="IntegralLimit", property="integralLimit", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralLimitAge", property="integralLimitAge", jdbcType=JdbcType.BIT),
        @Result(column="IntegralByRegister", property="integralByRegister", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByBirthday", property="integralByBirthday", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByFirstTime", property="integralByFirstTime", jdbcType=JdbcType.INTEGER),
        @Result(column="IntegralByRegisterStatus", property="integralByRegisterStatus", jdbcType=JdbcType.BIT),
        @Result(column="IntegralByBirthdayStatus", property="integralByBirthdayStatus", jdbcType=JdbcType.BIT),
        @Result(column="IntegralByFirstTimeStatus", property="integralByFirstTimeStatus", jdbcType=JdbcType.BIT),
        @Result(column="CreateUserId", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateUserName", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateUserId", property="updateUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="UpdateUserName", property="updateUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="UpdateUserDate", property="updateUserDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="IsEffective", property="isEffective", jdbcType=JdbcType.TINYINT)
    })
    IntegralRule selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRuleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRule record);

    @Update({
        "update marketing_integral_rule",
        "set TimeLimitStatus = #{timeLimitStatus,jdbcType=BIT},",
          "TimeLimit = #{timeLimit,jdbcType=TIMESTAMP},",
          "IntegralLimitStatus = #{integralLimitStatus,jdbcType=BIT},",
          "IntegralLimit = #{integralLimit,jdbcType=INTEGER},",
          "IntegralLimitAge = #{integralLimitAge,jdbcType=BIT},",
          "IntegralByRegister = #{integralByRegister,jdbcType=INTEGER},",
          "IntegralByBirthday = #{integralByBirthday,jdbcType=INTEGER},",
          "IntegralByFirstTime = #{integralByFirstTime,jdbcType=INTEGER},",
          "IntegralByRegisterStatus = #{integralByRegisterStatus,jdbcType=BIT},",
          "IntegralByBirthdayStatus = #{integralByBirthdayStatus,jdbcType=BIT},",
          "IntegralByFirstTimeStatus = #{integralByFirstTimeStatus,jdbcType=BIT},",
          "CreateUserId = #{createUserId,jdbcType=INTEGER},",
          "CreateUserName = #{createUserName,jdbcType=VARCHAR},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "UpdateUserId = #{updateUserId,jdbcType=INTEGER},",
          "UpdateUserName = #{updateUserName,jdbcType=VARCHAR},",
          "UpdateUserDate = #{updateUserDate,jdbcType=TIMESTAMP},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "IsEffective = #{isEffective,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRule record);
}