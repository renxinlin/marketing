package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.ExchangeUpDownStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("H5兑换值对象")

public class H5SalerRuleExchangeDto implements Serializable {

    @NotNull
    @Min(value = 0,message = "兑换ID不可为null")
    @ApiModelProperty(value = "兑换ID不可为null")
    private Long id;




}


