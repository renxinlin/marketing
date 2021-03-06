package com.jgw.supercodeplatform.marketing.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class GlobalRamCache {
	//过期时间十分钟
	private final static long expireMills = 600;
	//扫码时保存产品和码信息到内存，待授权后根据授权state值获取
	private String  MARKETING_GLOBAL_SCAN_CODE_INFO="marketing:cache:scanCodeInfo";
	private String  MARKETING_GLOBAL_CACHE ="marketing:cache:wxMerchants";

	@Autowired
	private MarketingWxMerchantsMapper mWxMerchantsMapper;

	@Autowired
	private RedisUtil redisUtil;




	public  ScanCodeInfoMO getScanCodeInfoMO(String wxsate) {
		if (StringUtils.isBlank(wxsate)) {
			throw new SuperCodeExtException("获取扫码缓存信息时参数wxsate不能为空", 500);
		}
		String json = redisUtil.get(MARKETING_GLOBAL_SCAN_CODE_INFO+":"+wxsate);
		ScanCodeInfoMO scanCodeInfoMO = JSONObject.parseObject(json, ScanCodeInfoMO.class);
		return scanCodeInfoMO;
	}

	public void putScanCodeInfoMO(String wxsate, ScanCodeInfoMO scanCodeInfoMO) {
		if (StringUtils.isBlank(wxsate)) {
			throw new SuperCodeExtException("wxstae为空", 500);
		}
		if(scanCodeInfoMO == null){
			throw new SuperCodeExtException("扫码信息为空", 500);
		}
		redisUtil.set(MARKETING_GLOBAL_SCAN_CODE_INFO + ":" + wxsate, JSON.toJSONString(scanCodeInfoMO), expireMills);
	}


	public void deleteScanCodeInfoMO(String wxsate) {
		if (StringUtils.isBlank(wxsate)) {
			throw new SuperCodeExtException("wxstae为空", 500);
		}
		redisUtil.remove(MARKETING_GLOBAL_SCAN_CODE_INFO + ":" + wxsate);

	}



	public MarketingWxMerchants getWXMerchants(String organizationId) {
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeExtException("GlobalRamCache获取MarketingWxMerchants缓存时参数组织id不能为空", 500);
		}
		MarketingWxMerchants mWxMerchants=null;
		String json = (String) redisUtil.hmGet(MARKETING_GLOBAL_CACHE, organizationId);
		if (json != null) {
			mWxMerchants=JSONObject.parseObject(json, MarketingWxMerchants.class);
		}
		//从数据库中查询
		if (mWxMerchants == null) {
			mWxMerchants = mWxMerchantsMapper.selectByOrganizationId(organizationId);
			//查看该用户是否为使用甲骨文的
			if (mWxMerchants != null && mWxMerchants.getMerchantType() != null && mWxMerchants.getMerchantType().intValue() == 1) {
				MarketingWxMerchants jgwMarketingWxMerchants = new MarketingWxMerchants();
				Long jgwId = mWxMerchants.getJgwId();
				if (jgwId != null) {
					jgwMarketingWxMerchants = mWxMerchantsMapper.getJgw(jgwId);
				} else {
					jgwMarketingWxMerchants = mWxMerchantsMapper.getDefaultJgw();
				}
				jgwMarketingWxMerchants.setOrganizatioIdlName(mWxMerchants.getOrganizatioIdlName());
				jgwMarketingWxMerchants.setOrganizationId(mWxMerchants.getOrganizationId());
				return jgwMarketingWxMerchants;
			}
			if (mWxMerchants != null) {
				redisUtil.hmSet (MARKETING_GLOBAL_CACHE,organizationId, JSONObject.toJSONString(mWxMerchants));
			}
		}
		if (null==mWxMerchants) {
			throw new SuperCodeExtException("无法根据组织id="+organizationId+"获取组织商户公众号信息", 500);
		}
		return mWxMerchants;
	}

	public void delWXMerchants(String organizationId) {
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeExtException("GlobalRamCache获取MarketingWxMerchants缓存时参数组织id不能为空", 500);
		}
		redisUtil.deleteHmKey(MARKETING_GLOBAL_CACHE, organizationId);
	}

}
