package com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("马关联的产品")
public class IntegralProductAggDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("1、自动获取 2、仅当前")
    private Integer autoType;

    private List<IntegralProductDto> productDtos;


}
