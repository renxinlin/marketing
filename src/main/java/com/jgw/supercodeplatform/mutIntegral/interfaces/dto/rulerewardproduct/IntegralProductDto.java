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
@ApiModel("马关联的产品")
public class IntegralProductDto implements Serializable {

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



    private String productBatchId;

    /**
     * 产品批次名称
     */
    private String productBatchName;

    /**
     * 活动产品Id
     */
    private String productId;

    /**
     * 活动产品名称
     */
    private String productName;



}
