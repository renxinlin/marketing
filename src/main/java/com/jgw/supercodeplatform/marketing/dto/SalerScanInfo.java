package com.jgw.supercodeplatform.marketing.dto;

import lombok.Data;

/**
 * 导购扫码es 数据体
 */
@Data
public class SalerScanInfo {


    private Long activitySetId;//活动设置主键Id
    private Long activityId;//活动主键Id
    private String productBatchId;//批次号
    private String productId;//活动产品Id
    private Byte referenceRole;//


    private String codeId;//外码,跳转到营销扫码接口时获取
    private String codeTypeId;//码值id,跳转到营销扫码接口时获取


    private String organizationId;//当前扫码所属企业id,跳转到营销扫码接口时获取
    private Long userId;
    private String openId;//当前扫码用户openid授权接口获取
    private String mobile;//登录的手机号
    private Byte memberType;  // 默认1导购员,其他员工等

    private long scanCodeTime;//将用于定时任务检查清除已经长时间未用的扫码缓存 yyyy-MM-dd HH:mm:ss



}
