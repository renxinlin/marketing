package com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("积分批次产品")

public class IntegralProductBatchDto {
    @ApiModelProperty("产品批次ID")
    private String productBatchId;

    /**
     * 产品批次名称
     */
    @ApiModelProperty("产品批次名称")
    private String productBatchName;



}
