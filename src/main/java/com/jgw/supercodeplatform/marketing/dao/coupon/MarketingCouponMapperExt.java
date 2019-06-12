package com.jgw.supercodeplatform.marketing.dao.coupon;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.MarketingCouponMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingCoupon;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketingCouponMapperExt extends MarketingCouponMapper, CommonSql {
    String bizField = " ActivitySetId, CouponAmount, DeductionDate, DeductionProductType, DeductionChannelType ";
    @Insert(startScript +
            " insert into marketing_coupon ( ActivitySetId, " +
            " CouponAmount, DeductionDate, "+
            " DeductionProductType, DeductionChannelType ) values " +
            " <foreach collection='list' item='channel' index='index' separator=','> " +
            " (" +
            " #{channel.activitySetId}, " +
            " #{channel.couponAmount}, " +
            " #{channel.deductionDate}, " +
            " #{channel.deductionProductType}, " +
            " #{channel.deductionChannelType} " +
            " ) " +
            " </foreach> " +
            endScript)
    int batchInsert(@Param("list") List<MarketingCoupon> list);

    @Select(" select " + bizField + " from marketing_coupon where ActivitySetId = #{activitySetId} ")
    List<MarketingCoupon> selectByActivitySetId(Long activitySetId);



    @Delete("delete from marketing_coupon where ActivitySetId = #{activitySetId} ")
    int deleteByActivitySetId(@Param("activitySetId") Long activitySetId);
}
