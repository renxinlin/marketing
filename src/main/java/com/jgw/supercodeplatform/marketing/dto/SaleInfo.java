package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("销售员中心")
public class SaleInfo {
    @ApiModelProperty("扫码个数")
    private Integer scanQRCodeNum;
    @ApiModelProperty("红包个数")
    private Integer scanAmoutNum;
    @ApiModelProperty("红包金额")
    private Float amoutNum;
    @ApiModelProperty("红包金额字符串")
    private String amoutNumStr;
    @ApiModelProperty("分页数据 红包中奖|未中奖记录")
    private  AbstractPageService.PageResults<IntegralRecord> pageInfo;
}
