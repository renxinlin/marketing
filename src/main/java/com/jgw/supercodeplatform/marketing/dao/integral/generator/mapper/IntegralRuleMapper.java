package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.IntegralRuleSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface IntegralRuleMapper {
    @Delete({
        "delete from marketing_integral_rule",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_integral_rule (id, time_limit_status, ",
        "time_limit, integral_limit_status, ",
        "integral_limit, integral_by_register, ",
        "integral_by_birthday, integral_by_first_time, ",
        "integral_by_register_status, integral_by_birthday_status, ",
        "integral_by_first_time_status, create_user_id, ",
        "create_user_name, create_date, ",
        "update_user_id, update_user_name, ",
        "update_user_date, organization_id, ",
        "organization_name, is_effective, ",
        "PerAge)",
        "values (#{id,jdbcType=BIGINT}, #{timeLimitStatus,jdbcType=BIT}, ",
        "#{timeLimit,jdbcType=TIMESTAMP}, #{integralLimitStatus,jdbcType=BIT}, ",
        "#{integralLimit,jdbcType=INTEGER}, #{integralByRegister,jdbcType=INTEGER}, ",
        "#{integralByBirthday,jdbcType=INTEGER}, #{integralByFirstTime,jdbcType=INTEGER}, ",
        "#{integralByRegisterStatus,jdbcType=BIT}, #{integralByBirthdayStatus,jdbcType=BIT}, ",
        "#{integralByFirstTimeStatus,jdbcType=BIT}, #{createUserId,jdbcType=INTEGER}, ",
        "#{createUserName,jdbcType=VARCHAR}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{updateUserId,jdbcType=INTEGER}, #{updateUserName,jdbcType=VARCHAR}, ",
        "#{updateUserDate,jdbcType=TIMESTAMP}, #{organizationId,jdbcType=INTEGER}, ",
        "#{organizationName,jdbcType=VARCHAR}, #{isEffective,jdbcType=TINYINT}, ",
        "#{perage,jdbcType=BIT})"
    })
    int insert(IntegralRule record);

    @InsertProvider(type= IntegralRuleSqlProvider.class, method="insertSelective")
    int insertSelective(IntegralRule record);

    @Select({
        "select",
        "id, time_limit_status, time_limit, integral_limit_status, integral_limit, integral_by_register, ",
        "integral_by_birthday, integral_by_first_time, integral_by_register_status, integral_by_birthday_status, ",
        "integral_by_first_time_status, create_user_id, create_user_name, create_date, ",
        "update_user_id, update_user_name, update_user_date, organization_id, organization_name, ",
        "is_effective, PerAge",
        "from marketing_integral_rule",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="time_limit_status", property="timeLimitStatus", jdbcType=JdbcType.BIT),
        @Result(column="time_limit", property="timeLimit", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="integral_limit_status", property="integralLimitStatus", jdbcType=JdbcType.BIT),
        @Result(column="integral_limit", property="integralLimit", jdbcType=JdbcType.INTEGER),
        @Result(column="integral_by_register", property="integralByRegister", jdbcType=JdbcType.INTEGER),
        @Result(column="integral_by_birthday", property="integralByBirthday", jdbcType=JdbcType.INTEGER),
        @Result(column="integral_by_first_time", property="integralByFirstTime", jdbcType=JdbcType.INTEGER),
        @Result(column="integral_by_register_status", property="integralByRegisterStatus", jdbcType=JdbcType.BIT),
        @Result(column="integral_by_birthday_status", property="integralByBirthdayStatus", jdbcType=JdbcType.BIT),
        @Result(column="integral_by_first_time_status", property="integralByFirstTimeStatus", jdbcType=JdbcType.BIT),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_user_name", property="createUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_user_name", property="updateUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_user_date", property="updateUserDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.INTEGER),
        @Result(column="organization_name", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_effective", property="isEffective", jdbcType=JdbcType.TINYINT),
        @Result(column="PerAge", property="perage", jdbcType=JdbcType.BIT)
    })
    IntegralRule selectByPrimaryKey(Long id);

    @UpdateProvider(type=IntegralRuleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IntegralRule record);

    @Update({
        "update marketing_integral_rule",
        "set time_limit_status = #{timeLimitStatus,jdbcType=BIT},",
          "time_limit = #{timeLimit,jdbcType=TIMESTAMP},",
          "integral_limit_status = #{integralLimitStatus,jdbcType=BIT},",
          "integral_limit = #{integralLimit,jdbcType=INTEGER},",
          "integral_by_register = #{integralByRegister,jdbcType=INTEGER},",
          "integral_by_birthday = #{integralByBirthday,jdbcType=INTEGER},",
          "integral_by_first_time = #{integralByFirstTime,jdbcType=INTEGER},",
          "integral_by_register_status = #{integralByRegisterStatus,jdbcType=BIT},",
          "integral_by_birthday_status = #{integralByBirthdayStatus,jdbcType=BIT},",
          "integral_by_first_time_status = #{integralByFirstTimeStatus,jdbcType=BIT},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "create_user_name = #{createUserName,jdbcType=VARCHAR},",
          "create_date = #{createDate,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=INTEGER},",
          "update_user_name = #{updateUserName,jdbcType=VARCHAR},",
          "update_user_date = #{updateUserDate,jdbcType=TIMESTAMP},",
          "organization_id = #{organizationId,jdbcType=INTEGER},",
          "organization_name = #{organizationName,jdbcType=VARCHAR},",
          "is_effective = #{isEffective,jdbcType=TINYINT},",
          "PerAge = #{perage,jdbcType=BIT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(IntegralRule record);
}