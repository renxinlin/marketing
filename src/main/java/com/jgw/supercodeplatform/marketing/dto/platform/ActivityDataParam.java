package com.jgw.supercodeplatform.marketing.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@ApiModel("活动数据时间查询入参")
public class ActivityDataParam {
    @ApiModelProperty("开始日期")
    @NotNull(message = "开始日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @ApiModelProperty("结束日期")
    @NotNull(message = "结束日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
