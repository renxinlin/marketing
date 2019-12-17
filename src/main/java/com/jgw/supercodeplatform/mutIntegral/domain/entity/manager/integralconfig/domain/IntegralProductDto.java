package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain;

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
public class IntegralProductDto implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * Id
     */
    private Long id;

    /**
     * 积分主键
     */
    private Long integralRuleId;

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
     * 建立日期
     */
    private Date createDate;

    /**
     * 修改日期
     */
    private Date updateDate;

    /**
     * 是否自动获取(1、自动获取 2、仅此一次 )当前仅当为大转盘和签到有效
     */
    private Integer autoType;

    private String organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

}
