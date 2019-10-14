package com.jgw.supercodeplatform.marketing.dto;

import java.util.List;

import lombok.Data;

@Data
public class OuterCodesEntity {
	
	private List<List<OuterCode>> outerCodeIdList;
	
	@Data
	public static class OuterCode{
		
		private String outCodeId;
		
		private String codeTypeId;
		
		private Integer currentNumber;
		
		private Boolean codeShow;
		
		private Boolean display;
		
	}

}
