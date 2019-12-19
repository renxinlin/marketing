package com.jgw.supercodeplatform.mutIntegral.infrastructure.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IntegralMessageInfo {
    private String reciver;
    private String content;
}
