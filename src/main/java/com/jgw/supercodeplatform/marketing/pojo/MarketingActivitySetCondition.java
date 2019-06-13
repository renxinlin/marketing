package com.jgw.supercodeplatform.marketing.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MarketingActivitySetCondition {


    /**
     * 活动门槛: 使用方式:
     * 每次产品迭代出新的门槛条件
     * 在这里加字段，以及字段使用方式说明
     */

    @ApiModelProperty(name = "eachDayNumber", value = "每人每天最多参与", example = "1")
    private Integer eachDayNumber;
    /**
     *  定义统一枚举
     *  0 无条件
     *  1 协助领红包
     *  2 协助领积分
     */
    @ApiModelProperty(name = "participationCondition", value = "0无条件 1协助领红包 2协助领积分", example = "1")
    private Byte participationCondition;

    /**
     * 消耗积分数值
     */
    @ApiModelProperty(name = "consumeIntegral", value = "消耗积分", example = "1")
    private Integer consumeIntegral;

    /**
     *  抵扣券条件
     */
    @ApiModelProperty(name = "acquireCondition", value = "获得条件: 1首次积分,2一次积分达到,3累计积分达到,4参加获得抵扣券的产品", example = "1")
    private Byte acquireCondition;

    /**
     * 抵扣券条件中的积分
     */
    @ApiModelProperty(name = "acquireCondition", value = "累计积分或者一次积分数值", example = "1")
    private Integer acquireConditionIntegral;


    /**
     * 不选默认所有渠道
     */
    @ApiModelProperty(name = "allChannels", value = "不选默认所有渠道0存在选择渠道，1所有渠道", example = "1")
    private Integer allChannels;



    // 反序列化待定
    // JSONObject.parseObject(s1, MarketingActivitySetCondition.class);

    /**
     * 自定义字符序列化
     * @return
     */
    public String toJsonString(){
        String objectString = JSON.toJSONString(this);
        if(objectString.equals("{}")){
            return null;
        }
        return objectString;
    }
    
}
