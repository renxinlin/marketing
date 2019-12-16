package com.jgw.supercodeplatform.mutIntegral.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("积分规则通用")
public class IntegralRuleDto implements Serializable {



    @Range(min = 1,max = 2,message = "积分有效期填写不合法")
    @ApiModelProperty("积分有效期1永久2到期时间")  private Integer expiredType;

    @ApiModelProperty("积分有效期过期时间")        private Date expiredDate;

    @ApiModelProperty("积分上限")                 private Integer integralLimit;

    @Range(min = 1,max = 2,message = "积分上限")
    @ApiModelProperty("1无上限2有上限")           private Integer integralLimitType;

    @NotNull(message = "通用积分规则必须设置")
    @ApiModelProperty("通用积分规则")           private List<IntegralRuleRewardCommonDto> integralRuleRewardCommons;


}
