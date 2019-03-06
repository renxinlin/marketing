package com.jgw.supercodeplatform.marketing.weixinpay.requestparam;

import org.apache.commons.lang.StringUtils;

public class OrganizationPayRequestParam {
	private String mch_appid; // 申请商户号的appid或商户号绑定的appid
	private String mchid;// 微信支付分配的商户号
	private String nonce_str;// 随机字符串，不长于32位
	private String sign;// 随机字符串，不长于32位
	private String partner_trade_no;// 随机字符串，不长于32位
	private String openid;// 随机字符串，不长于32位
	private String check_name;// NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
	private int amount;
	private String desc;// 企业付款备注，必填。注意：备注中的敏感词会被转成字符*
	private String spbill_create_ip;// 该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。

	public String getMch_appid() {
		return mch_appid;
	}

	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPartner_trade_no() {
		return partner_trade_no;
	}

	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCheck_name() {
		if (StringUtils.isBlank(check_name)) {
			return "NO_CHECK";
		}
		return check_name;
	}

	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDesc() {
		if (StringUtils.isBlank(desc)) {
			return "营销企业付款到零钱";
		}
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

}
