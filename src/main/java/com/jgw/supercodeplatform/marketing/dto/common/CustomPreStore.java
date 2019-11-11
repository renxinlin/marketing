package com.jgw.supercodeplatform.marketing.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("自定义预存数据")
public class CustomPreStore {

    @ApiModelProperty(value = "码制", required = true)
    @NotBlank(message = "码制不能为空")
    private String codeType;

    @ApiModelProperty(value = "码", required = true)
    @NotBlank(message = "码不能为空")
    private String code;

    @ApiModelProperty(value = "存储类型<防止以后还需要预存其他数据，可根据类型来指定>，1：存储码平台uuid,", required = true)
    @NotNull(message = "存储类型不能为空")
    private Integer storeType;

    @ApiModelProperty(value = "存储的数据", required = true)
    @NotBlank(message = "存储数据不能为空")
    private String storeData;

}
