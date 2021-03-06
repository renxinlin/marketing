package com.jgw.supercodeplatform.marketing.dto.coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增优惠券活动参数
 */
@ApiModel("新增优惠券活动参数")
@Data
public class MarketingActivityCouponAddParam {



    @ApiModelProperty(name = "activityTitle", value = "活动标题", example = "小活动")
    private String activityTitle;//活动标题

    @ApiModelProperty(name = "eachDayNumber", value = "每人每天次数", example = "2")
    private Integer eachDayNumber;//每人每天次数


    @ApiModelProperty(name = "autoFetch", value = "活动码数量(1、自动追加 2、仅当前数量 ) ", example = "1")
    private Integer autoFetch;//是否自动获取(1、自动获取 2、仅此一次 )

    @ApiModelProperty(name = "acquireCondition", value = "获得条件: 1首次积分,2一次积分达到,3累计积分达到,4参加获得抵扣券的产品", example = "1")
    @NotNull(message = "获得条件不能为空")
    @Range(min = 1,max = 4, message = "数值必须在1到4之间")
    private Byte acquireCondition;
    @ApiModelProperty(name = "acquireCondition", value = "累计积分或者一次积分数值", example = "1")
    private Integer acquireConditionIntegral;


    @ApiModelProperty(name = "activityStartDate", value = "活动开始时间", example = "2017-01-11")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date activityStartDate;//活动开始时间

    @ApiModelProperty(name = "activityEndDate", value = "活动结束时间", example = "2217-01-11")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date activityEndDate;//活动结束时间


    @ApiModelProperty(value = "活动设置产品参数")
    private List<MarketingActivityProductParam> productParams = new ArrayList<>();


    @ApiModelProperty(value = "渠道")
    private List<MarketingChannelParam> channelParams = new ArrayList<>();


    @ApiModelProperty(value = "抵扣券规则")
    private MarketingCouponVo coupon;

}
