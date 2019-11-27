package com.jgw.supercodeplatform.two.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author fangshiping
 * @date 2019/11/14 14:18
 */
@Data
@ApiModel(value = "会员绑定手机")
public class MarketingMembersBindMobileParam {
    @NotNull(message = "id不为空")
    private Long id;
    @NotBlank(message = "组织id不能为空")
    private String organizationId;
    @NotBlank(message = "手机不能为空")
    private String mobile;
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;
}
