package com.jgw.supercodeplatform.marketing.dto.platform;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@ApiModel("参与记录入参")
public class JoinResultPage extends DaoSearch {
    @ApiModelProperty("活动ID")
    @NotNull(message = "活动ID不能为空")
    @Positive(message = "活动ID必须为正数")
    private Long activitySetId;

}
