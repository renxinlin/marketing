package com.jgw.supercodeplatform.marketing.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("扫码活跃会员")
public class ScanCodeMemberDataVo {
    @ApiModelProperty("总会员量")
    private Long totalMemberNum;
    @ApiModelProperty("活跃会员")
    private Long activeMemberNum;
    @ApiModelProperty("活跃率")
    private String activeRate;
}
