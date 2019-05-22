package com.jgw.supercodeplatform.marketing.common.model.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import lombok.Data;

import java.util.List;

@Data
public class MarketingSalerActivitySetMO {

    private Long id;

    /**
     * 活动设置id
     */
    private Long activityId;

    /**
     * 活动标题
     */
    private String activityTitle;

    /**
     * 活动开始时间
     */
    private String activityStartDate;

    /**
     * 活动结束时间
     */
    private String activityEndDate;

    /**
     * 更新的用户名称
     */
    private String updateUserName;

    /**
     * 组织名称
     */
    private String organizationIdName;

    /**
     * 活动状态(1、表示上架进展，0 表示下架)
     */
    private Integer activityStatus;

    /**
     * 关联的活动渠道信息集合
     */
    private List<MarketingChannel> marketingChannels;

    /**
     * 关联的活动产品信息集合
     */
    private List<MarketingActivityProduct> maActivityProducts;
}
