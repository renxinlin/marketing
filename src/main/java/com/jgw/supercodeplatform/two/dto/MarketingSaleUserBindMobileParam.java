package com.jgw.supercodeplatform.two.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/11/14 14:19
 */
@Data
@ApiModel("导购员绑定手机")
public class MarketingSaleUserBindMobileParam {
    private Long id;
    private String organizationId;
    private String mobile;
    private String verificationCode;
}
