package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class IntegralSingleCodeDto implements Serializable {

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
     * 单码积分
     */
    private String singleCode;
    private String organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

}
