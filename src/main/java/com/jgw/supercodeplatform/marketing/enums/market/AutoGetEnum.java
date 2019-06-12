package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 活动码追加方式
 */
@AllArgsConstructor
@NoArgsConstructor
public enum AutoGetEnum {

    BY_AUTO(1,"自动追加"),
    BY_NOT_AUTO(2,"仅此一次;仅当前数量");
    /**
     * 业务类型
     */
    @Getter
    @Setter
    private int auto;
    @Getter
    @Setter
    private String desc ;
}
