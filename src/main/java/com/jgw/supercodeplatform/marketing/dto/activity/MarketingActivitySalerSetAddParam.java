package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "导购员导购活动新建model")
public class MarketingActivitySalerSetAddParam {

	@ApiModelProperty(name = "activityTitle", value = "活动标题", example = "小活动")
    private String activityTitle;//活动标题
	
	@ApiModelProperty(name = "activityStartDate", value = "活动开始时间", example = "2017-01-11")
    private String activityStartDate;//活动开始时间
	
	@ApiModelProperty(name = "activityEndDate", value = "活动结束时间", example = "2217-01-11")
    private String activityEndDate;//活动结束时间
	
	@ApiModelProperty(name = "eachDayNumber", value = "每人每天次数", example = "2")
    private Integer eachDayNumber;//每人每天次数


	@ApiModelProperty(name = "autoFetch", value = "活动码数量(1、自动追加 2、仅当前数量 ) ", example = "1")
	private Integer autoFetch;//是否自动获取(1、自动获取 2、仅此一次 )

	@ApiModelProperty(name = "participationCondition", value = "0无条件 1协助领红包 2协助领积分", example = "1")
	private Byte participationCondition;

    
}
