package com.jgw.supercodeplatform.marketing.weixinpay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WXPayMarketingConfig extends WXPayConfig{
	protected static Logger logger = LoggerFactory.getLogger(WXPayMarketingConfig.class);
	private String appId;
	private String mchId;
	private String key;
	private String certificatePath;//证书路径为完整绝对路径
	

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

	@Override
	InputStream getCertStream() {
		if (StringUtils.isBlank(certificatePath)) {
			return null;
		}
		File file=new File(certificatePath);
		if (!file.exists()) {
			logger.error("证书路径："+certificatePath+"，对应的证书不存在");
		}
		FileInputStream in=null;
		try {
			in=new FileInputStream(certificatePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}

	@Override
	IWXPayDomain getWXPayDomain() {
		return new IWXPayMarketingDomain();
	}

}
