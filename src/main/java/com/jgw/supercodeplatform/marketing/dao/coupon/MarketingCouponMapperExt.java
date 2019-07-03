package com.jgw.supercodeplatform.marketing.dao.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.MarketingCouponMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingCoupon;

@Mapper
public interface MarketingCouponMapperExt extends MarketingCouponMapper, CommonSql {
    String bizField = "Id, ActivitySetId, CouponAmount, DeductionStartDate,DeductionEndDate, DeductionProductType, DeductionChannelType ";
    @Insert(startScript +
            " insert into marketing_coupon ( ActivitySetId, "+
    		" OrganizationId, OrganizationName, "+
            " CouponAmount, DeductionStartDate, DeductionEndDate, "+
            " DeductionProductType, DeductionChannelType, CreateTime) values "+
            " <foreach collection='list' item='channel' index='index' separator=','>"+
            " (" +
            " #{channel.activitySetId}," +
            " #{channel.organizationId},"+
            " #{channel.organizationName},"+
            " #{channel.couponAmount}," +
            " #{channel.deductionStartDate}," +
            " #{channel.deductionEndDate}," +
            " #{channel.deductionProductType}," +
            " #{channel.deductionChannelType}," +
            " NOW())"+
            "</foreach>"+
            endScript)
    int batchInsert(@Param("list") List<MarketingCoupon> list);

    @Select(" select " + bizField + " from marketing_coupon where ActivitySetId = #{activitySetId} ")
    List<MarketingCoupon> selectByActivitySetId(Long activitySetId);



    @Delete("delete from marketing_coupon where ActivitySetId = #{activitySetId} ")
    int deleteByActivitySetId(@Param("activitySetId") Long activitySetId);
}
