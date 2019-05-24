package com.jgw.supercodeplatform.marketing.dto.activity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MarketingMemberAndScanCodeInfoParam implements Serializable {

    private static final long serialVersionUID = 5417880309568164695L;

    private String codeId;//外码,跳转到营销扫码接口时获取

    private String codeTypeId;//码值id,跳转到营销扫码接口时获取

    private String productId;//产品id,跳转到营销扫码接口时获取

    private String productBatchId;//码平台传的产品批次id,跳转到营销扫码接口时获取

    private String openId;//当前扫码用户openid授权接口获取

    private Long activitySetId;//当前扫码的码参与的活动设置id,跳转到营销扫码接口时获取

    private String organizationId;//当前扫码所属企业id,跳转到营销扫码接口时获取

    private String createTime;//将用于定时任务检查清除已经长时间未用的扫码缓存 yyyy-MM-dd HH:mm:ss

    private String mobile;//登录的手机号

    private Long userId;//用户id

    private String userName;

    private Byte memberType;
}
