package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("导购员绑定手机")
public class MarketingSaleUserBindMobileParam {
    private Long id;
    private String mobile;
    private String verificationCode;

}
