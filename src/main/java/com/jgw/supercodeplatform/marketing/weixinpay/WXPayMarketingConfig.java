package com.jgw.supercodeplatform.marketing.weixinpay;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.mybatisplusdao.MarketingWxMerchantsExtMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchantsExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
@Slf4j
public class WXPayMarketingConfig extends WXPayConfig{
 	private String appId;
	private String mchId;
	private String key;
	private String certificatePath;//证书路径为完整绝对路径
	private String certificatePassword;//证书密码

	public void setAppId(String appId) {
		this.appId = appId;
	}


	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	String getAppID() {
		return appId;
	}

	@Override
	String getMchID() {
		return mchId;
	}

	@Override
	String getKey() {
		return key;
	}

	public String getCertificatePassword() {
		return certificatePassword;
	}


	public void setCertificatePassword(String certificatePassword) {
		this.certificatePassword = certificatePassword;
	}


	@Override
	InputStream getCertStream() {

		MarketingWxMerchantsMapper marketingWxMerchantsMapper = SpringContextUtil.getBean(MarketingWxMerchantsMapper.class);
		MarketingWxMerchantsExtMapper marketingWxMerchantsExtMapper = SpringContextUtil.getBean(MarketingWxMerchantsExtMapper.class);
		List<MarketingWxMerchants> marketingWxMerchantsList = marketingWxMerchantsMapper.getByAppidMchid(mchId, appId);
		if (CollectionUtils.isEmpty(marketingWxMerchantsList)) {
			return null;
		}
		List<MarketingWxMerchantsExt> mwExtList = marketingWxMerchantsExtMapper.selectList(Wrappers.<MarketingWxMerchantsExt>query().eq("appid", appId).eq("OrganizationId", marketingWxMerchantsList.get(0).getOrganizationId()));
		if (CollectionUtils.isEmpty(mwExtList)) {
			log.error("证书路径："+certificatePath+"，对应的证书不存在");
			return null;
		}
		InputStream in=null;
		byte[] certBytes = mwExtList.get(0).getCertificateInfo();
		in= new ByteArrayInputStream(certBytes);
		return in;
	}

	@Override
	IWXPayDomain getWXPayDomain() {
		return new IWXPayMarketingDomain();
	}

}
