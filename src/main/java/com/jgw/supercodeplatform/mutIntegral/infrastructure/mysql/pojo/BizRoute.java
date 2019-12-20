package com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("market_new_biz_route")
public class BizRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 0其他10多级积分
     */
    @TableField("BizType")
    private Integer bizType;

    /**
     * 1单码2号段3产品批次ID4产品ID5批次
     */
    @TableField("Priority")
    private Integer priority;

    /**
     * 单码
     */
    @TableField("SingleCode")
    private String singleCode;

    /**
     * 号段起始
     */
    @TableField("StartSegmentCode")
    private String startSegmentCode;

    /**
     * 号段結束码
     */
    @TableField("EndSegmentCode")
    private String endSegmentCode;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizationName")
    private String organizationName;

    /**
     * 生码批次
     */
    @TableField("SbatchId")
    private String sbatchId;

    @TableField("ProductId")
    private String productId;

    @TableField("ProductBatchId")
    private String productBatchId;

    /**
     * 业务url
     */
    @TableField("BizUrl")
    private String bizUrl;


}
