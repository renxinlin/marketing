package com.jgw.supercodeplatform.marketing.weixinpay;

public class IWXPayMarketingDomain implements IWXPayDomain{

	@Override
	public void report(String domain, long elapsedTimeMillis, Exception ex) {
		System.out.println("IWXPayMarketingDomain:report:"+ex);
	}

	@Override
	public DomainInfo getDomain(WXPayConfig config) {
		DomainInfo info=new DomainInfo("api.mch.weixin.qq.com", true);
		return info;
	}

}
