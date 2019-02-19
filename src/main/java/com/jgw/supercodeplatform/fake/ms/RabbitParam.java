package com.jgw.supercodeplatform.fake.ms;


import java.util.Map;

import com.jgw.supercodeplatform.fake.enums.ms.RabbitBusinessTypeEnum;
import com.jgw.supercodeplatform.fake.enums.ms.RabbitTopicEnum;

/** 发送消息公共参数实体类
 * @author Created by jgw136 on 2018/01/27.
 */
public class RabbitParam {
    private RabbitTopicEnum rabbitTopic;
    private RabbitBusinessTypeEnum rabbitBusinessType;
    private Map<String,Object> params;

    public RabbitTopicEnum getRabbitTopic() {
        return rabbitTopic == null ? RabbitTopicEnum.MS_COUDE_COMMON : rabbitTopic;
    }

    public void setRabbitTopic(RabbitTopicEnum rabbitTopic) {
        this.rabbitTopic = rabbitTopic;
    }

    public RabbitBusinessTypeEnum getRabbitBusinessType() {
        return rabbitBusinessType == null ? RabbitBusinessTypeEnum.PUBLIC_SEND : rabbitBusinessType;
    }

    public void setRabbitBusinessType(RabbitBusinessTypeEnum businessType) {
        this.rabbitBusinessType = businessType;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public RabbitParam(){}

    public RabbitParam(RabbitTopicEnum rabbitTopic, RabbitBusinessTypeEnum businessType, Map<String, Object> params) {
        this.rabbitTopic = rabbitTopic;
        this.rabbitBusinessType = businessType;
        this.params = params;
    }

    @Override
    public String toString() {
        return "RabbitParam{" +
                "rabbitTopic=" + rabbitTopic +
                ", businessType=" + rabbitBusinessType +
                ", params=" + params +
                '}';
    }
}
