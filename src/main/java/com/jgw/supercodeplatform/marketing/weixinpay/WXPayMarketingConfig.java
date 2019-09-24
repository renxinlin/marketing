package com.jgw.supercodeplatform.marketing.weixinpay;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.mybatisplusdao.MarketingWxMerchantsExtMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchantsExt;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class WXPayMarketingConfig extends WXPayConfig{
	protected static Logger logger = LoggerFactory.getLogger(WXPayMarketingConfig.class);
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
		MarketingWxMerchants marketingWxMerchants = marketingWxMerchantsMapper.getByAppidMchid(mchId, appId);
		if (marketingWxMerchants == null) {
			return null;
		}
		MarketingWxMerchantsExt mwExt = marketingWxMerchantsExtMapper.selectOne(Wrappers.<MarketingWxMerchantsExt>query().eq("organizationId", marketingWxMerchants.getOrganizationId()));
		if (mwExt == null) {
			logger.error("证书路径："+certificatePath+"，对应的证书不存在");
		}
		InputStream in=null;
		byte[] certBytes = mwExt.getCertificateInfo();
		in= new ByteArrayInputStream(certBytes);
		return in;
	}

	@Override
	IWXPayDomain getWXPayDomain() {
		return new IWXPayMarketingDomain();
	}

}
