package com.jgw.supercodeplatform.marketing.dto.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("url")
public class UrlParam {

    @ApiModelProperty(value = "url", required = true)
    @NotBlank(message = "url不能为空")
    private String url;

}
