package com.jgw.supercodeplatform.marketing.weixinpay;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
