package com.jgw.supercodeplatform.marketingsaler.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**销售员中心 预填信息
 * @author fangshiping
 * @date 2019/12/2 14:32
 */
@Data
public class SalerPreFillInfoVo {
    @ApiModelProperty("收货人")
    private String dinghuoren;
    @ApiModelProperty("联系电话")
    private String dinghuorendianhua;
    @ApiModelProperty("收获地址")
    private String shouhuodizhi;

}
