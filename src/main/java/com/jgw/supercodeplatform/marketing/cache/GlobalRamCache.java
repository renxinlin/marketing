package com.jgw.supercodeplatform.marketing.cache;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
@Component
public class GlobalRamCache {

	//扫码时保存产品和码信息到内存，待授权后根据授权state值获取
	private String  MARKETING_GLOBAL_SCAN_CODE_INFO="marketing:cache:scanCodeInfo";
	private String  MARKETING_GLOBAL_CACHE ="marketing:cache:wxMerchants";
	
	@Autowired
	private MarketingWxMerchantsMapper mWxMerchantsMapper;
	
	@Autowired
	private RedisUtil redisUtil;




	public void putScanCodeInfoMO(String wxsate, ScanCodeInfoMO scanCodeInfoMO) throws SuperCodeException {
		if (StringUtils.isBlank(wxsate)) {
			throw new SuperCodeException("wxstae为空", 500);
		}
		if(scanCodeInfoMO == null){
			throw new SuperCodeException("扫码信息为空", 500);
		}
	    
		redisUtil.hmSet(MARKETING_GLOBAL_SCAN_CODE_INFO, wxsate,JSONObject.toJSONString(scanCodeInfoMO));
	}


	public Long deleteScanCodeInfoMO(String wxsate) throws SuperCodeException {
		if (StringUtils.isBlank(wxsate)) {
			throw new SuperCodeException("wxstae为空", 500);
		}
		return redisUtil.deleteHmKey(MARKETING_GLOBAL_SCAN_CODE_INFO, wxsate);

	}



	public  ScanCodeInfoMO getScanCodeInfoMO(String wxsate) throws SuperCodeException {
		if (StringUtils.isBlank(wxsate)) {
			throw new SuperCodeException("获取扫码缓存信息时参数wxsate不能为空", 500);
		}
		String json =(String) redisUtil.hmGet(MARKETING_GLOBAL_SCAN_CODE_INFO, wxsate);
		ScanCodeInfoMO scanCodeInfoMO=JSONObject.parseObject(json, ScanCodeInfoMO.class);
		if (null==scanCodeInfoMO) {
			throw new SuperCodeException("根据wxsate="+wxsate+"无法获取扫码缓存信息请重新扫码", 500);
		}
		
		return scanCodeInfoMO;
	}

	public  MarketingWxMerchants getWXMerchants(String organizationId) throws SuperCodeException {
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("GlobalRamCache获取MarketingWxMerchants缓存时参数组织id不能为空", 500);
		}
		 MarketingWxMerchants mWxMerchants=null;
		String json = (String) redisUtil.hmGet(MARKETING_GLOBAL_CACHE, organizationId);
		if (null==json) {
			// 多节点后可以重复拉取该数据
			if (null==mWxMerchants) {
				mWxMerchants=mWxMerchantsMapper.selectByOrganizationId(organizationId);
				redisUtil.hmSet (MARKETING_GLOBAL_CACHE,organizationId, JSONObject.toJSONString(mWxMerchants));
			}
		}else {
			mWxMerchants=JSONObject.parseObject(json, MarketingWxMerchants.class);
		}
		if (null==mWxMerchants) {
			throw new SuperCodeException("无法根据组织id="+organizationId+"获取组织商户公众号信息", 500);
		}
		return mWxMerchants;
	}

}
