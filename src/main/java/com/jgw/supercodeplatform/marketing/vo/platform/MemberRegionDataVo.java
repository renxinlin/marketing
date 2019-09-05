package com.jgw.supercodeplatform.marketing.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("会员地域分布")
public class MemberRegionDataVo {
    @ApiModelProperty("地域名")
    private String region;
    @ApiModelProperty("会员数量")
    private Long memberNum;

}
