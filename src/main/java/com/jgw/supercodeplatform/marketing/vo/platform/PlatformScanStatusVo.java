package com.jgw.supercodeplatform.marketing.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("当前是否有活动正在启用，当前扫码是否可用")
public class PlatformScanStatusVo {
    @ApiModelProperty("当前是否有全网活动正在运营，true:是，false:否")
    private Boolean platformStatus;
    @ApiModelProperty("当前扫码是否可用，true:是<码未被扫过，可用>，false:否<不可用，码已经被扫过抽奖或者已经放弃过抽奖>")
    private Boolean scanStatus;
}
