package com.jgw.supercodeplatform.two.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("2.0会员")
    private Long id;

    @ApiModelProperty("2.0会员挂的组织")
    @NotBlank(message = "组织id不能为空")
    private String organizationId;

    /**
     * 可能存在库里 也可能不存在库里
     */
    @ApiModelProperty("绑定的手机号")
    @NotBlank(message = "手机不能为空")
    private String mobile;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;
}
