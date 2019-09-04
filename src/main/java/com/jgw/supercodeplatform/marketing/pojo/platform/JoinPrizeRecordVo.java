package com.jgw.supercodeplatform.marketing.pojo.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@ApiModel("参与记录")
public class JoinPrizeRecordVo {
    @ApiModelProperty("中奖金额")
    private Float winningAmount;
    @ApiModelProperty("中奖码")
    private String winningCode;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("组织名称")
    private String organizationFullName;
    @ApiModelProperty(value = "参与时间", dataType = "java.lang.String")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("抽奖结果")
    private String winningResult;
}
