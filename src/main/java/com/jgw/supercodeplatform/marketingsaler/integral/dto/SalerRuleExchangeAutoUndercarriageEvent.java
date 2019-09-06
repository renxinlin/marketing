package com.jgw.supercodeplatform.marketingsaler.integral.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 自动下架事件
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SalerRuleExchangeAutoUndercarriageEvent {
    private Long id;
    /**
     * 兑换活动状态0上架1手动下架2自动下架
     */
    private Byte status = 2;
}
