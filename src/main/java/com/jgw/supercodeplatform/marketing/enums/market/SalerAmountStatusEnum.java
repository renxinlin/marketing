package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SalerAmountStatusEnum {

    ACQUIRE_SUCCESS("1","获得"), ACQUIRE_FAIL("2","未获得"),
    SEND_FAIL("3","发送失败"), SEND_SUCCESS("4","发送成功");

    public final String status;

    public final String desc;

}
