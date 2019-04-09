package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.DeliveryAddressMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DeliveryAddressMapperExt extends DeliveryAddressMapper, CommonSql {
    static String allFeilds = " Id id, MemberId memberId, MemberName memberName, Name name, Mobile mobile, Province province, City city, Country country, " +
            " Street street, ProvinceCode provinceCode, CityCode cityCode, CountryCode countryCode, StreetCode streetCode, Detail detail, Postcode, postcode. DefaultUsing defaultUsing";
    @Select(startScript + " select " + allFeilds+ " from marketing_delivery_address where MemberId = #{memberId}" + endScript)
    List<DeliveryAddress> selectByMemberId(@Param("memberId") Long memberId);

    @Select(startScript + " select " + allFeilds+ " from marketing_delivery_address where MemberId = #{memberId} and DefaultUsing = 0 " + endScript)
    DeliveryAddress havingUsing(@Param("memberId") Long memberId);
}