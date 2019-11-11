package com.jgw.supercodeplatform.marketing.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动默认值参数
 */
public class ActivityDefaultConstant {

    /**存储活动ID对应bizType
     *
     */
    public final static Map<Long, Integer> ActivityBizTypeMap = new HashMap();

    static {
        //微信红包和锦鲤翻牌的ID分别为1，2对应bizType都为5
        ActivityBizTypeMap.put(1L, 5);
        ActivityBizTypeMap.put(2L, 5);
        //抵扣券对应bizType为6
        ActivityBizTypeMap.put(4L, 6);
        //大转盘对应bizType为7
        ActivityBizTypeMap.put(7L, 7);
        //签到活动对应bizType为8
        ActivityBizTypeMap.put(8L, 8);
    }

    /**
     * 系统每人每天上限默认值
     */
    public static final int eachDayNum =200;
    /**
     * 默认的活动结束时间
     */
    public static final String activityEndDate ="2099-01-01";
    public static final String superToken ="super-token";
}
