package com.jgw.supercodeplatform.marketing.common.model.activity;

public class LotteryResultMO {
	private Byte awardType;

	private String msg;

	private int winnOrNot;

	private Object data;
	
	public Byte getAwardType() {
		return awardType;
	}

	public void setAwardType(Byte awardType) {
		this.awardType = awardType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getWinnOrNot() {
		return winnOrNot;
	}

	public void setWinnOrNot(int winnOrNot) {
		this.winnOrNot = winnOrNot;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
