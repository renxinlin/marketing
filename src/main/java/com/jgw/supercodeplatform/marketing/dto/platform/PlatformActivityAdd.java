package com.jgw.supercodeplatform.marketing.dto.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel("添加活动")
public class PlatformActivityAdd {
    @ApiModelProperty("活动标题<不可重复>")
    @NotBlank(message = "活动标题不能为空")
    private String activityTitle;
    @ApiModelProperty(value = "活动开始时间", dataType = "java.lang.String")
    @NotNull(message = "活动开始时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "时间必须为当前时间或者未来的时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activityStartDate;
    @ApiModelProperty(value = "活动结束时间", dataType = "java.lang.String")
    @NotNull(message = "活动结束时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "时间必须为未来的时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activityEndDate;
    @ApiModelProperty("奖次信息")
    @Valid
    @NotEmpty(message = "奖次信息不能为空")
    private List<PrizeType> prizeTypeList;
    @ApiModelProperty("每人最多参与次数")
    @NotNull(message = "参与次数不能为空")
    @Positive(message = "参与次数必须为一个正数")
    private Integer maxJoinNum;
    @ApiModelProperty("资源链")
    private String sourceLink;
    @ApiModelProperty("参与组织")
    @Valid
    @NotEmpty(message = "参与组织不能为空")
    private List<JoinOrganization> joinOrganizationList;

    @Setter
    @Getter
    @ApiModel("奖品红包")
    public static class PrizeType {
        @ApiModelProperty("红包金额")
        @NotNull(message = "红包金额不能为空")
        @Positive(message = "红包金额必须为一个正数")
        private Float prizeAmount;
        @ApiModelProperty("中奖概率<不含百分号>")
        @NotNull(message = "中奖概率不能为空")
        @Positive(message = "中奖概率必须为一个正数")
        private Integer prizeProbability;
        @ApiModelProperty("该奖项剩余次数")
        @NotNull(message = "剩余中奖次数不能为空")
        @Positive(message = "剩余中奖次数必须为一个正数")
        private Integer remainingNumber;

    }

    @Setter
    @Getter
    @ApiModel("参与组织")
    public static class JoinOrganization {
        @ApiModelProperty("组织ID")
        @NotBlank(message = "组织ID不能为空")
        private String organizationId;
        @ApiModelProperty("组织名称")
        @NotBlank(message = "组织名称不能为空")
        private String organizationFullName;

    }

}

