package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 大转盘网页对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("大转盘新增")
public class WheelsUpdateDto {


    @NotEmpty(message = "id不可为空") @Min(0)
    @ApiModelProperty("id")private Long  id;

    @NotEmpty(message = "活动模板id不可为空")
    @ApiModelProperty("活动模板id") private String  templateId;

    @NotEmpty(message = "标题1不可为空")
    @ApiModelProperty("标题1")  private String title1;

    @NotEmpty(message = "标题2不可为空")
    @ApiModelProperty("标题2")  private String title2;

    @NotEmpty(message = "标题3不可为空")
    @ApiModelProperty("标题3")  private String title3;

    @ApiModelProperty("企业logo") private String logo;

    @NotNull(message = "活动时间不可为空")
    @ApiModelProperty("活动开始时间")  private Date startTime;

    @NotNull(message = "活动时间不可为空")
    @ApiModelProperty("活动结束时间")  private Date endTime;

    @ApiModelProperty("公众号二维码")   private String wxErcode;

    @ApiModelProperty("第三方链接按钮")   private String thirdUrlButton;

    @ApiModelProperty("第三方链接") private String thirdUrl;

    @ApiModelProperty("奖励总数") private Integer prizeNum;

    @Valid @NotNull(message = "参加活动的产品不存在")
    @ApiModelProperty("大转盘产品集合")  private List<ProductUpdateDto> productUpdateDto;

    @Valid @NotNull(message = "活动奖励不可为空")
    @ApiModelProperty("大转盘产品集合")  private List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos;
}
