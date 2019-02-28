package com.jgw.supercodeplatform.marketing.cache;

import java.util.HashMap;
import java.util.Map;

import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;

public class GlobalRamCache {
    //扫码时保存产品和码信息到内存，待授权后根据授权state值获取
	public static Map<String,ScanCodeInfoMO> scanCodeInfoMap=new HashMap<String, ScanCodeInfoMO>();
	
}
