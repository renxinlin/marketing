package com.jgw.supercodeplatform.marketing.enums.ms;

/** 消息订阅主题
 * @author Created by jgw136 on 2018/01/27.
 */
public enum RabbitTopicEnum {

    MS_CODE("ms-code"),REDIS("redis"),ES("es"),MS_COUDE_COMMON("ms-code-common"),MS_TEST_USER("test-user");

    private String topic;
    RabbitTopicEnum(String topic){
        setTopic(topic);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
