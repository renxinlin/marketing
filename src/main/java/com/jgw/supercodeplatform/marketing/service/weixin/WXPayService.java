package com.jgw.supercodeplatform.marketing.service.weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.asyntask.WXPayAsynTask;
import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeNoMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPay;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayConstants.SignType;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayMarketingConfig;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayUtil;
import com.jgw.supercodeplatform.marketing.weixinpay.requestparam.OrganizationPayRequestParam;

@Service
public class WXPayService {
    @Autowired
    private WXPayTradeNoMapper wxTradeNoMapper;
    
    @Autowired
    private MarketingWxMerchantsMapper mWxMerchantsMapper;
    
    @Autowired
    private SpringContextUtil springContextUtil;
    
    private static ExecutorService exec=Executors.newFixedThreadPool(20);
    
    /**
     * 企业付款到零钱
     * @param openid
     * @param spbill_create_ip
     * @param amount
     * @param organizationId
     * @throws Exception
     */
	public void qiyePay(String  openid,String  spbill_create_ip,int amount,String mobile,String  organizationId,MarketingMembersWinRecord redWinRecord) throws Exception {
		MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.get(organizationId);
		String key=mWxMerchants.getMerchantKey();
		String partner_trade_no=WXPayTradeNoGenerator.tradeNo();
		//设置配置类
		WXPayMarketingConfig config=new WXPayMarketingConfig();
		config.setAppId(mWxMerchants.getMchAppid());
		config.setKey(key);
		config.setMchId(mWxMerchants.getMchid());
		
		//封装请求参数实体
		OrganizationPayRequestParam oRequestParam=new OrganizationPayRequestParam();
		oRequestParam.setAmount(amount);
		oRequestParam.setMch_appid(mWxMerchants.getMchAppid());
		oRequestParam.setOpenid(openid);
		oRequestParam.setNonce_str(WXPayUtil.generateNonceStr());
		oRequestParam.setPartner_trade_no(partner_trade_no);
		oRequestParam.setSpbill_create_ip(spbill_create_ip);
		//根据实体类转换成签名map
		Map<String, String> signMap=generateMap(oRequestParam);
		
		//获取签名值sign
		String sign=WXPayUtil.generateSignature(signMap, key, SignType.MD5);
		signMap.put("sign", sign);

		WXPay wxPay=new WXPay(config);
		exec.submit(new WXPayAsynTask(wxPay, WechatConstants.ORGANIZATION_PAY_CHANGE_URL, signMap, 1000, 5000,redWinRecord));
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
