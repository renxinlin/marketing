package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * CDK值对象
 * </p>
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WheelsRewardCdk implements Serializable {

    private Long id;
    private Long prizeRewardId;

    private String cdk;

    /**
     * 1未领取 2 待领取 3 领取
     */
    private Integer status;


}
