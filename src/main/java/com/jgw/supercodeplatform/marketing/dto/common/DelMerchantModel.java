package com.jgw.supercodeplatform.marketing.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DelMerchantModel {
    @NotBlank(message = "组织ID不能为空")
    private String organizationId;
    @NotBlank(message = "appid不能为空")
    private String appid;
}
