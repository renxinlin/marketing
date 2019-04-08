package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralRuleMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IntegralRuleMapperExt extends IntegralRuleMapper {

    @Select({
        "select",
        "Id, TimeLimitStatus, TimeLimitDate, IntegralLimitStatus, IntegralLimit, IntegralLimitAge, ",
        "IntegralByRegister, IntegralByBirthday, IntegralByFirstTime, IntegralByRegisterStatus, ",
        "IntegralByBirthdayStatus, IntegralByFirstTimeStatus, OrganizationId, OrganizationName",
        "from marketing_integral_rule",
        "where OrganizationId = #{organizationId}"
    })
	IntegralRule selectByOrgId(@Param("organizationId")String organizationId);
}