package com.jgw.supercodeplatform.marketing.weixinpay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

public class WXPayMarketingConfig extends WXPayConfig{

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
