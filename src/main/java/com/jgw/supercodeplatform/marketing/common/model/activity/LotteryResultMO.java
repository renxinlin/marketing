package com.jgw.supercodeplatform.marketing.common.model.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("中奖结果")
public class LotteryResultMO {
	@ApiModelProperty("奖品类型")
	private Byte awardType;
	@ApiModelProperty("奖品显示提示语")
	private String msg;
	@ApiModelProperty("是否抽中奖品")
	private int winnOrNot;
	@ApiModelProperty("红包发放审核（0:不审核，1：需要审核）")
	private byte sendAudit;
	@ApiModelProperty("奖品数据")
	private Object data;

	public LotteryResultMO(String msg) {
		this.msg = msg;
	}

	public LotteryResultMO(int winnOrNot){
		this.winnOrNot = winnOrNot;
	}

}
