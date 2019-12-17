package com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@ApiModel("无须做马关联的单码设置")
public class IntegralSingleCodeDto implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * Id
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 积分主键
     */
    @ApiModelProperty("积分设置主表ID")

    private Long integralRuleId;

    /**
     * 单码积分
     */
    @ApiModelProperty("单码积分")
    private String singleCode;


}
