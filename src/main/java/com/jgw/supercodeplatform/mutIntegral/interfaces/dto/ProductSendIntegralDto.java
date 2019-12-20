package com.jgw.supercodeplatform.mutIntegral.interfaces.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
@ApiModel("积分派送")
public class ProductSendIntegralDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "请填写手机")
    @ApiModelProperty("会员")
    private String memberMobile;

    @NotEmpty(message = "请填写备注")
    @ApiModelProperty("备注")
    private String remark;

    @NotNull(message = "请填写积分")
    @Min(value = 0,message = "积分必须大于0")
    @ApiModelProperty("积分")
    private Integer integralNum;


}
