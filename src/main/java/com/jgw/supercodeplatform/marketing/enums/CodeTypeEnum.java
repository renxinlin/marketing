package com.jgw.supercodeplatform.marketing.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 码制
 * @author JGW
 *
 */
@AllArgsConstructor
public enum CodeTypeEnum {

	LOGISTICS("20", "20位物流码"), SEQUENCE("14", "16位顺序码");
	
	@Getter
	private String typeId;
	@Getter
	private String desc;
	
	public static CodeTypeEnum getCodeTypeEnum(String typeId) {
		CodeTypeEnum[] codeTypeEnums = CodeTypeEnum.values();
		for(CodeTypeEnum codeTypeEnum : codeTypeEnums) {
			if(codeTypeEnum.typeId.equals(typeId)) {
                return codeTypeEnum;
            }
		}
		return null;
	}
	
}
