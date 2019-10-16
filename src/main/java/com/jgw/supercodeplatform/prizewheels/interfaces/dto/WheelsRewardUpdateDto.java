package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("大转盘奖励含id")
public class WheelsRewardUpdateDto {

    /**
     * 奖励类型:1 虚拟2 实物
     */
    @NotNull
    @Min(1) @Max(2)
    @ApiModelProperty("奖励类型:1 虚拟2 实物")    private Integer type;


    @NotNull(message = "奖励概率不可为空") @Min(0) @Max(100)
    @ApiModelProperty("奖励概率")   private double probability;


    @ApiModelProperty("奖励图片")
    private String picture;


    @ApiModelProperty("上传文件信息")
    private List<CdkKey> cdkKey;


    @NotNull(message = "id不可为空") @Min(0)
    @ApiModelProperty("id") private Long id;


    @NotNull(message = "活动id不可为空") @Min(0)
    @ApiModelProperty("活动id") private Long prizeWheelId;

    @NotEmpty(message = "奖项名称不可为空")
    @ApiModelProperty("獎項名")
    private String name;


}
