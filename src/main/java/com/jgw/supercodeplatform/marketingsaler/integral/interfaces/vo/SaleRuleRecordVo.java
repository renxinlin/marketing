package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
@ApiModel("销售员积分实体")
public class SaleRuleRecordVo {
    @ApiModelProperty("积分数值")
    private int haveIntegral;
    @ApiModelProperty("积分记录")
    private List<SalerRecord> salerRecord;
}
