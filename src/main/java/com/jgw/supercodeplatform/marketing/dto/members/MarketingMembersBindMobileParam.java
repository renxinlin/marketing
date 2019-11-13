package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/11/12 15:14
 */
@Data
@ApiModel(value = "会员绑定手机")
public class MarketingMembersBindMobileParam {
    private Long id;
    private String mobile;
    private String verificationCode;
}
