package com.jgw.supercodeplatform.two.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/11/14 14:18
 */
@Data
@ApiModel(value = "会员绑定手机")
public class MarketingMembersBindMobileParam {
    private String mobile;
    private String verificationCode;
}
