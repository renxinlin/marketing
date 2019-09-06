package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_saler_rule_reward")
@ApiModel("产品id集合")
public class ProductDto {
    List<String> productIds;
}
