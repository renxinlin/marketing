package com.jgw.supercodeplatform.marketing.service.weixin;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.asyntask.WXPayAsynTask;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPay;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayConstants.SignType;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayMarketingConfig;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayUtil;
import com.jgw.supercodeplatform.marketing.weixinpay.requestparam.OrganizationPayRequestParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WXPayService {
	protected static Logger logger = LoggerFactory.getLogger(WXPayService.class);
    @Autowired
    private MarketingWxMerchantsMapper mWxMerchantsMapper;
    
    @Value("${weixin.certificate.path}")
    private String certificatePath;
    
    private static ExecutorService exec=Executors.newFixedThreadPool(20);
    
    
    /**
     * 企业付款到零钱
     * @param openid
     * @param spbill_create_ip
     * @param amount
     * @param organizationId
     * @param organizationId2 
     * @throws Exception
     */
	public void qiyePay(String  openid,String  spbill_create_ip,int amount,String  partner_trade_no, String organizationId) throws Exception {
		if (StringUtils.isBlank(openid) || StringUtils.isBlank(spbill_create_ip)|| StringUtils.isBlank(partner_trade_no)|| StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("发起微信支付参数不能为空,openid="+openid+",spbill_create_ip="+spbill_create_ip+",partner_trade_no="+partner_trade_no+spbill_create_ip+",organizationId="+organizationId, 500);
		}
		MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.get(organizationId);
		if (null==mWxMerchants) {
			throw new SuperCodeException("当前企业"+organizationId+"未绑定公众号数据", 500);
		}
		if (mWxMerchants.getMerchantType() == 1) {
			mWxMerchants = mWxMerchantsMapper.getJgw();
		} else if (StringUtils.isBlank(mWxMerchants.getCertificateAddress())) {
			throw new SuperCodeException("当前企业"+organizationId+"没有上传公众号证书", 500);
		}
		String mechid=mWxMerchants.getMchid();
		String mechappid=mWxMerchants.getMchAppid();
		if (StringUtils.isBlank(mechid) || StringUtils.isBlank(mechappid)) {
			throw new SuperCodeException("获取到的企业公众号支付参数有空值，mechid="+mechid+",mechappid="+mechappid, 500);
		}

		String key=mWxMerchants.getMerchantKey();
		//设置配置类
		WXPayMarketingConfig config=new WXPayMarketingConfig();
		config.setAppId(mechappid);
		config.setKey(key);
		config.setMchId(mechid);

		String wholePath=certificatePath+File.separator+organizationId+File.separator+mWxMerchants.getCertificateAddress();
		logger.info("微信企业支付到零钱证书完整路径："+wholePath);
		config.setCertificatePath(wholePath);
		//封装请求参数实体
		OrganizationPayRequestParam oRequestParam=new OrganizationPayRequestParam();
		oRequestParam.setAmount(amount);
		oRequestParam.setMch_appid(mechappid);
		oRequestParam.setOpenid(openid);
		oRequestParam.setNonce_str(WXPayUtil.generateNonceStr());
		oRequestParam.setPartner_trade_no(partner_trade_no);
		oRequestParam.setSpbill_create_ip(spbill_create_ip);
		oRequestParam.setMchid(mechid);
		//根据实体类转换成签名map
		Map<String, String> signMap=generateMap(oRequestParam);

		//获取签名值sign
		String sign=WXPayUtil.generateSignature(signMap, key, SignType.MD5);
		signMap.put("sign", sign);

		WXPay wxPay=new WXPay(config);
		exec.submit(new WXPayAsynTask(wxPay, WechatConstants.ORGANIZATION_PAY_CHANGE_Suffix_URL, signMap, 1000, 5000));
	}


	/**
	 * 企业支付零钱同步
	 * @param openid
	 * @param spbill_create_ip
	 * @param amount
	 * @param partner_trade_no
	 * @param organizationId
	 * @throws Exception
	 */
	public void qiyePaySync(String  openid,String  spbill_create_ip,int amount,String  partner_trade_no, String organizationId) throws Exception {
		if (StringUtils.isBlank(openid) || StringUtils.isBlank(spbill_create_ip)|| StringUtils.isBlank(partner_trade_no)|| StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("发起微信支付参数不能为空,openid="+openid+",spbill_create_ip="+spbill_create_ip+",partner_trade_no="+partner_trade_no+spbill_create_ip+",organizationId="+organizationId, 500);
		}
		MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.get(organizationId);
		if (null==mWxMerchants) {
			throw new SuperCodeException("当前企业"+organizationId+"未绑定公众号数据", 500);
		}
		if (mWxMerchants.getMerchantType() == 1) {
			mWxMerchants = mWxMerchantsMapper.getJgw();
		} else if (StringUtils.isBlank(mWxMerchants.getCertificateAddress())) {
			throw new SuperCodeException("当前企业"+organizationId+"没有上传公众号证书", 500);
		}
		String mechid=mWxMerchants.getMchid();
		String mechappid=mWxMerchants.getMchAppid();
		if (StringUtils.isBlank(mechid) || StringUtils.isBlank(mechappid)) {
			throw new SuperCodeException("获取到的企业公众号支付参数有空值，mechid="+mechid+",mechappid="+mechappid, 500);
		}

		String key=mWxMerchants.getMerchantKey();
		//设置配置类
		WXPayMarketingConfig config=new WXPayMarketingConfig();
		config.setAppId(mechappid);
		config.setKey(key);
		config.setMchId(mechid);

		String wholePath=certificatePath+File.separator+organizationId+File.separator+mWxMerchants.getCertificateAddress();
		logger.info("微信企业支付到零钱证书完整路径："+wholePath);
		config.setCertificatePath(wholePath);
		//封装请求参数实体
		OrganizationPayRequestParam oRequestParam=new OrganizationPayRequestParam();
		oRequestParam.setAmount(amount);
		oRequestParam.setMch_appid(mechappid);
		oRequestParam.setOpenid(openid);
		oRequestParam.setNonce_str(WXPayUtil.generateNonceStr());
		oRequestParam.setPartner_trade_no(partner_trade_no);
		oRequestParam.setSpbill_create_ip(spbill_create_ip);
		oRequestParam.setMchid(mechid);
		//根据实体类转换成签名map
		Map<String, String> signMap=generateMap(oRequestParam);

		//获取签名值sign
		String sign=WXPayUtil.generateSignature(signMap, key, SignType.MD5);
		signMap.put("sign", sign);

		WXPay wxPay=new WXPay(config);
		WXPayAsynTask wxPayAsynTask = new WXPayAsynTask(wxPay, WechatConstants.ORGANIZATION_PAY_CHANGE_Suffix_URL, signMap, 1000, 5000);
		wxPayAsynTask.run();

	}
	
	
    /**
     * 生成参与签名的数据map
     * @param oRequestParam
     * @return
     */
	private Map<String, String> generateMap(OrganizationPayRequestParam oRequestParam) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("mch_appid", oRequestParam.getMch_appid());
		map.put("mchid", oRequestParam.getMchid());
		map.put("nonce_str", oRequestParam.getNonce_str());
		map.put("partner_trade_no", oRequestParam.getPartner_trade_no());
		map.put("openid", oRequestParam.getOpenid());
		map.put("check_name", oRequestParam.getCheck_name());
		map.put("amount", String.valueOf(oRequestParam.getAmount()));
		map.put("desc", oRequestParam.getDesc());
		map.put("spbill_create_ip", oRequestParam.getSpbill_create_ip());
		return map;
	}
}
