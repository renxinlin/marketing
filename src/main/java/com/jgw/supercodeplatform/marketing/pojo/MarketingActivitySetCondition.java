package com.jgw.supercodeplatform.marketing.pojo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.modelmapper.ModelMapper;

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
    @ApiModelProperty(name = "participationCondition", value = "参与红包条件", example = "1")
    private Byte participationCondition;

    /**
     * 消耗积分数值
     */
    @ApiModelProperty(name = "consumeIntegral", value = "消耗积分", example = "1")
    private Integer consumeIntegral;
    // 反序列化待定
    // JSONObject.parseObject(s1, MarketingActivitySetCondition.class);

    /**
     * 自定义字符序列化
     * @return
     */
    public String toJsonString(){
        // {"":"","":""}
        StringBuffer sb = new StringBuffer("{");
        if(eachDayNumber != null ){
            sb.append("\"eachDayNumber\":").append(eachDayNumber);

        }
        // 加逗号
        if(participationCondition != null || !"".equals(participationCondition)){
            sb.append(",\"participationCondition\":").append(participationCondition);
        }

        if(eachDayNumber != null ){
            sb.append(",\"consumeIntegral\":").append(consumeIntegral);

        }

        sb.append("}");
        String objectString = sb.toString();
        if(objectString.equals("{}")){
            return null;
        }
        return objectString;
    }

    // 测试
    public static void main(String[] args) {
        MarketingActivitySetCondition object = new MarketingActivitySetCondition();
         String s = JSONObject.toJSONString(object);
        System.out.println(s);

        MarketingActivitySetCondition marketingActivitySetCondition = JSONObject.parseObject(s, MarketingActivitySetCondition.class);
        System.out.println(marketingActivitySetCondition);

        String s1 = object.toJsonString();


        MarketingActivitySetCondition marketingActivitySetCondition1 = JSONObject.parseObject(s1, MarketingActivitySetCondition.class);
        System.out.println(marketingActivitySetCondition1);
    }

    
}
