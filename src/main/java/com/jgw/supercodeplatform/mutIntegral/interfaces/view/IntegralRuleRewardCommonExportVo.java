package com.jgw.supercodeplatform.mutIntegral.interfaces.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("积分规则通用列表")
public class IntegralRuleRewardCommonExportVo {



    private String type;

    private String money;

    private String integral;

    private String expire;

    private String limit;

}
