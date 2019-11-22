package com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class SbatchUrlDto {

    @ApiModelProperty(value = "唯一id,新增不传,修改传")
    private String batchUrlId;

    @ApiModelProperty(value = "批次id")
    @NotNull(message = "sBatchId必传")
    private Long batchId;

    @ApiModelProperty(value = "url")
    @NotNull(message = "url必传")
    private String url;

    @ApiModelProperty(value = "业务类型 业务标识（1.营销积分,2.溯源,3.防伪,4.物流，5.营销活动【微信红包 锦鲤翻牌 】,6.营销抵扣券[活动锦囊，抵扣券] 7.大转盘 ,,8.签到）")
    @Range(min = 1,max = 6,message = "businessType的值只能为:1、2、3、4、5、6 ")
    private Integer businessType;

    @ApiModelProperty(value = "用户角色")
    private String clientRole;



    /**
     * 产品批次号
     */
    private String productBatchId;


    /**
     * 活动产品Id
     */
    private String productId;


}