package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("参与记录")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DaoSearchWithPrizeWheelsIdDto  extends DaoSearch {
    @NotNull(message = "活动不存在") @Min(value = 0,message = "活动不存在")
    @ApiModelProperty("大转盘活动id") private Long id;



}
