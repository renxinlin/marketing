package com.jgw.supercodeplatform.marketing.diagram.enums;

import java.util.UUID;

/**
 * 任务类型：不同的类型产生不同的数据结果
 */
public enum TaskTypeEnum {
    /**
     * 招募会员海报注册数
     */
    REGISTER_NUM("1","","招募会员海报注册数"),
    TOTAL_MEMBER("2","累计会员数",""),
    MEMBER_PORTRAIT("3","会员画像",""),
    STATISTICS("4","活动&积分参与统计",""),
    SALE("5","总产品销售额趋势",""),
    TOP6("6","TOP6产品积分",""),
    MEMBER_MAP("7","会员地域分布","");

    /**
     * 任务类型
     */
    private String type;

    /**
     * 任务描述
     */
    private String desc;


    /**
     * 任务扩展
     */
    private String extend;


    TaskTypeEnum(String type) {
        this.type = type;
    }

    TaskTypeEnum( String type, String desc, String extend) {
        this.type = type;
        this.desc = desc;
        this.extend = extend;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
//46+15+22+18+9+16+41+15