package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 产品域
 *
 *
 * 参与大转盘活动的产品
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Product implements Serializable {


    /**
     * Id
     */
    private Long id;

    /**
     * 活动设置主键Id
     */
    private Long activitySetId;

    /**
     * 类型
     */
    private String codeType;

    /**
     * 产品批次号
     */
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

    /**
     * 该批次关联码总数
     */
    private Integer codeTotalAmount;

    /**
     * 建立日期
     */
    private Date createDate;

    /**
     * 修改日期
     */
    private Date updateDate;

    /**
     * 活动大类0会员活动1导购活动
     */
    private Boolean referenceRole;

    /**
     * 生码批次ID
     */
    private String sbatchId;

    /**
     * 产品所属活动类型其他0大转盘1
     */
    private Integer type;


}
