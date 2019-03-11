package com.jgw.supercodeplatform.marketing.weixinpay;

public class IWXPayMarketingDomain implements IWXPayDomain{

	@Override
	public void report(String domain, long elapsedTimeMillis, Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public DomainInfo getDomain(WXPayConfig config) {
		DomainInfo info=new DomainInfo("api.mch.weixin.qq.com", true);
		return info;
	}

}
