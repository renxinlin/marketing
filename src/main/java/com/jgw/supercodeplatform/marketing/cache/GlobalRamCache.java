package com.jgw.supercodeplatform.marketing.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
@Component
public class GlobalRamCache {
	@Autowired
	private MarketingWxMerchantsMapper mWxMerchantsMapper;
	
    //扫码时保存产品和码信息到内存，待授权后根据授权state值获取
	public static Map<String,ScanCodeInfoMO> scanCodeInfoMap=new HashMap<String, ScanCodeInfoMO>();
	
	public static transient Map<String,MarketingWxMerchants> wxMerchantsMap=new HashMap<String, MarketingWxMerchants>();

	public  ScanCodeInfoMO getScanCodeInfoMO(String wxsate) throws SuperCodeException {
		if (StringUtils.isBlank(wxsate)) {
			throw new SuperCodeException("获取扫码缓存信息时参数wxsate不能为空", 500);
		}
		ScanCodeInfoMO scanCodeInfoMO=scanCodeInfoMap.get(wxsate);
		if (null==scanCodeInfoMO) {
			throw new SuperCodeException("根据wxsate="+wxsate+"无法获取扫码缓存信息请重新扫码", 500);
		}
		return scanCodeInfoMO;
	}
	
	public  MarketingWxMerchants getWXMerchants(String organizationId) throws SuperCodeException {
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("GlobalRamCache获取MarketingWxMerchants缓存时参数组织id不能为空", 500);
		}
		MarketingWxMerchants mWxMerchants=wxMerchantsMap.get(organizationId);
		if (null==mWxMerchants) {
			synchronized (this) {
				if (null==mWxMerchants) {
					mWxMerchants=mWxMerchantsMapper.selectByOrganizationId(organizationId);
					wxMerchantsMap.put(organizationId, mWxMerchants);
				}
			}
		}
		if (null==mWxMerchants) {
			throw new SuperCodeException("无法根据组织id="+organizationId+"获取组织商户公众号信息", 500);
		}
		return mWxMerchants;
	}
	
}
