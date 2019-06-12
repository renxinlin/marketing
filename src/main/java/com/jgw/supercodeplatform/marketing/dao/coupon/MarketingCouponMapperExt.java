package com.jgw.supercodeplatform.marketing.dao.coupon;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.MarketingCouponMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MarketingCouponMapperExt extends MarketingCouponMapper, CommonSql {

    @Insert(startScript +
            " insert into marketing_coupon ( ActivitySetId, "+
            " CouponAmount, DeductionStartDate, DeductionEndDate, "+
            " DeductionProductType, DeductionChannelType ) values "+
            "<foreach collection='list' item='channel' index='index' separator=','>"+
            "(" +
            "#{channel.activitySetId}," +
            "#{channel.couponAmount}," +
            "#{channel.deductionStartDate}," +
            "#{channel.deductionEndDate}," +
            "#{channel.deductionProductType}," +
            "#{channel.deductionChannelType}" +
            ")"+
            "</foreach>"+
            endScript)
    int batchInsert(@Param("list") List<MarketingChannel> list);
}
