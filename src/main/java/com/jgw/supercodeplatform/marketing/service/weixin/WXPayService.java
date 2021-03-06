package com.jgw.supercodeplatform.marketing.service.weixin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.asyntask.WXPayAsynTask;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.mybatisplusdao.MarketingWxMerchantsExtMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchantsExt;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPay;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayConstants.SignType;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayMarketingConfig;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayUtil;
import com.jgw.supercodeplatform.marketing.weixinpay.requestparam.OrganizationPayRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class WXPayService {
     @Autowired
    private MarketingWxMerchantsMapper mWxMerchantsMapper;
    
    @Value("${weixin.certificate.path}")
    private String certificatePath;
    
    private static ExecutorService exec=Executors.newFixedThreadPool(20);
	@Autowired
	private WXPayTradeOrderMapper wXPayTradeOrderMapper;

	@Autowired
	private MarketingWxMerchantsExtMapper marketingWxMerchantsExtMapper;
	/**
     * 企业付款到零钱
     * @param openid
     * @param spbill_create_ip
     * @param amount
     * @param organizationId
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
			if (mWxMerchants.getJgwId() != null) {
				mWxMerchants = mWxMerchantsMapper.getJgw(mWxMerchants.getJgwId());
			} else {
				mWxMerchants = mWxMerchantsMapper.getDefaultJgw();
			}
		} else if (StringUtils.isBlank(mWxMerchants.getCertificateAddress())) {
			throw new SuperCodeException("当前企业"+organizationId+"没有上传公众号证书", 500);
		}
		String mechid=mWxMerchants.getMchid();
		String mechappid=mWxMerchants.getMchAppid();
		if (StringUtils.isBlank(mechid) || StringUtils.isBlank(mechappid)) {
			throw new SuperCodeException("获取到的企业公众号支付参数有空值，mechid="+mechid+",mechappid="+mechappid, 500);
		}
		String certificatePassword = mWxMerchants.getCertificatePassword();
		String key=mWxMerchants.getMerchantKey();
		//设置配置类
		WXPayMarketingConfig config=new WXPayMarketingConfig();
		config.setAppId(mechappid);
		config.setKey(key);
		config.setMchId(mechid);
		if (StringUtils.isBlank(certificatePassword)) {
			config.setCertificatePassword(mWxMerchants.getMchid());
		} else {
			config.setCertificatePassword(certificatePassword);
		}
		String wholePath=certificatePath+File.separator+organizationId+File.separator+mWxMerchants.getCertificateAddress();
		log.info("微信企业支付到零钱证书完整路径："+wholePath);
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
	 * 微信同步支付
	 * @param openid
	 * @param spbill_create_ip
	 * @param amount
	 * @param partner_trade_no
	 * @param organizationId
	 * @throws Exception
	 */
	public void qiyePayAsycPlatform(String  openid,String  spbill_create_ip,int amount,String  partner_trade_no, String organizationId) throws Exception {
		if (StringUtils.isBlank(openid) || StringUtils.isBlank(spbill_create_ip)|| StringUtils.isBlank(partner_trade_no)|| StringUtils.isBlank(organizationId)) {
			log.info("发起微信支付参数不能为空,openid=" + openid + ",spbill_create_ip=" + spbill_create_ip + ",partner_trade_no=" + partner_trade_no + spbill_create_ip + ",organizationId=" + organizationId);
			return;
		}
		MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.get(organizationId);
		if (null==mWxMerchants) {
			log.info("当前企业"+organizationId+"未绑定公众号数据");
			return;
		}
		if (mWxMerchants.getMerchantType() == 1) {
			if (mWxMerchants.getJgwId() != null) {
				mWxMerchants = mWxMerchantsMapper.getJgw(mWxMerchants.getJgwId());
			} else {
				mWxMerchants = mWxMerchantsMapper.getDefaultJgw();
			}
		} else if (StringUtils.isBlank(mWxMerchants.getCertificateAddress())) {
			log.info("当前企业"+organizationId+"没有上传公众号证书");
			return;
		}
		String mechid=mWxMerchants.getMchid();
		String mechappid=mWxMerchants.getMchAppid();
		if (StringUtils.isBlank(mechid) || StringUtils.isBlank(mechappid)) {
			log.info("获取到的企业公众号支付参数有空值，mechid="+mechid+",mechappid="+mechappid);
			return;
		}
		String certificatePassword = mWxMerchants.getCertificatePassword();
		String key=mWxMerchants.getMerchantKey();
		//设置配置类
		WXPayMarketingConfig config=new WXPayMarketingConfig();
		config.setAppId(mechappid);
		config.setKey(key);
		config.setMchId(mechid);
		if (StringUtils.isBlank(certificatePassword)) {
			config.setCertificatePassword(mWxMerchants.getMchid());
		} else {
			config.setCertificatePassword(certificatePassword);
		}
		String wholePath=certificatePath+File.separator+organizationId+File.separator+mWxMerchants.getCertificateAddress();
		log.info("微信企业支付到零钱证书完整路径："+wholePath);
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
		new WXPayAsynTask(wxPay, WechatConstants.ORGANIZATION_PAY_CHANGE_Suffix_URL, signMap, 1000, 5000).run();
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
		log.info("支付参数 opendid={} spbill_create_ip={} amount={} partner_trade_no={} organizationId={}",openid,spbill_create_ip,amount,partner_trade_no,organizationId);

		if (StringUtils.isBlank(openid) || StringUtils.isBlank(spbill_create_ip)|| StringUtils.isBlank(partner_trade_no)|| StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("发起微信支付参数不能为空,openid="+openid+",spbill_create_ip="+spbill_create_ip+",partner_trade_no="+partner_trade_no+spbill_create_ip+",organizationId="+organizationId, 500);
		}
		MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.get(organizationId);
		if (null==mWxMerchants) {
			throw new SuperCodeException("当前企业"+organizationId+"未绑定公众号数据", 500);
		}
		if (mWxMerchants.getMerchantType() == 1) {
			if (mWxMerchants.getJgwId() != null) {
				mWxMerchants = mWxMerchantsMapper.getJgw(mWxMerchants.getJgwId());
			} else {
				mWxMerchants = mWxMerchantsMapper.getDefaultJgw();
			}
		} else if (StringUtils.isBlank(mWxMerchants.getCertificateAddress())) {
			throw new SuperCodeException("当前企业"+organizationId+"没有上传公众号证书", 500);
		}
		String mechid=mWxMerchants.getMchid();
		String mechappid=mWxMerchants.getMchAppid();
		if (StringUtils.isBlank(mechid) || StringUtils.isBlank(mechappid)) {
			throw new SuperCodeException("获取到的企业公众号支付参数有空值，mechid="+mechid+",mechappid="+mechappid, 500);
		}

		//保存订单
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WXPayTradeOrder tradeOrder = new WXPayTradeOrder();
		tradeOrder.setAmount((float)amount);
		tradeOrder.setOpenId(openid);
		tradeOrder.setTradeStatus((byte) 0);
		tradeOrder.setPartnerTradeNo(partner_trade_no);
		tradeOrder.setTradeDate(format.format(new Date()));
		tradeOrder.setOrganizationId(organizationId);
		wXPayTradeOrderMapper.insert(tradeOrder);


		String certificatePassword = mWxMerchants.getCertificatePassword();
		String key=mWxMerchants.getMerchantKey();
		//设置配置类
		WXPayMarketingConfig config=new WXPayMarketingConfig();
		config.setAppId(mechappid);
		config.setKey(key);
		config.setMchId(mechid);
		if (StringUtils.isBlank(certificatePassword)) {
			config.setCertificatePassword(mWxMerchants.getMchid());
		} else {
			config.setCertificatePassword(certificatePassword);
		}
		String wholePath=certificatePath+File.separator+mWxMerchants.getOrganizationId()+File.separator+mWxMerchants.getCertificateAddress();
		// 拉取db到磁盘
		cacheToDiscIfNecssary(wholePath,certificatePath+File.separator+mWxMerchants.getOrganizationId(), mWxMerchants.getOrganizationId());
		log.info("微信企业支付到零钱证书完整路径："+wholePath);
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
		wxPayAsynTask.pay();

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
	public void qiyePaySyncWithResend(String  openid,String  spbill_create_ip,int amount,String  partner_trade_no, String organizationId, int reSend) throws Exception {
		log.info("支付参数 opendid={} spbill_create_ip={} amount={} partner_trade_no={} organizationId={}",openid,spbill_create_ip,amount,partner_trade_no,organizationId);

		if (StringUtils.isBlank(openid) || StringUtils.isBlank(spbill_create_ip)|| StringUtils.isBlank(partner_trade_no)|| StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("发起微信支付参数不能为空,openid="+openid+",spbill_create_ip="+spbill_create_ip+",partner_trade_no="+partner_trade_no+spbill_create_ip+",organizationId="+organizationId, 500);
		}
		MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.get(organizationId);
		if (null==mWxMerchants) {
			throw new SuperCodeException("当前企业"+organizationId+"未绑定公众号数据", 500);
		}
		if (mWxMerchants.getMerchantType() == 1) {
			if (mWxMerchants.getJgwId() != null) {
				mWxMerchants = mWxMerchantsMapper.getJgw(mWxMerchants.getJgwId());
			} else {
				mWxMerchants = mWxMerchantsMapper.getDefaultJgw();
			}
		} else if (StringUtils.isBlank(mWxMerchants.getCertificateAddress())) {
			throw new SuperCodeException("当前企业"+organizationId+"没有上传公众号证书", 500);
		}
		String mechid=mWxMerchants.getMchid();
		String mechappid=mWxMerchants.getMchAppid();
		if (StringUtils.isBlank(mechid) || StringUtils.isBlank(mechappid)) {
			throw new SuperCodeException("获取到的企业公众号支付参数有空值，mechid="+mechid+",mechappid="+mechappid, 500);
		}

		//保存订单
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WXPayTradeOrder tradeOrder = new WXPayTradeOrder();
		tradeOrder.setAmount((float)amount);
		tradeOrder.setOpenId(openid);
		tradeOrder.setTradeStatus((byte) 0);
		tradeOrder.setPartnerTradeNo(partner_trade_no);
		tradeOrder.setTradeDate(format.format(new Date()));
		tradeOrder.setOrganizationId(organizationId);
		tradeOrder.setReSend(reSend);
		wXPayTradeOrderMapper.insert(tradeOrder);


		String certificatePassword = mWxMerchants.getCertificatePassword();
		String key=mWxMerchants.getMerchantKey();
		//设置配置类
		WXPayMarketingConfig config=new WXPayMarketingConfig();
		config.setAppId(mechappid);
		config.setKey(key);
		config.setMchId(mechid);
		if (StringUtils.isBlank(certificatePassword)) {
			config.setCertificatePassword(mWxMerchants.getMchid());
		} else {
			config.setCertificatePassword(certificatePassword);
		}
		String wholePath=certificatePath+File.separator+mWxMerchants.getOrganizationId()+File.separator+mWxMerchants.getCertificateAddress();
		// 拉取db到磁盘
		cacheToDiscIfNecssary(wholePath,certificatePath+File.separator+mWxMerchants.getOrganizationId(), mWxMerchants.getOrganizationId());
		log.info("微信企业支付到零钱证书完整路径："+wholePath);
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
		wxPayAsynTask.pay();

	}

	/**
	 *
 	 * @param wholeName
	 * @param wholePath
	 * @param organizationId  唯一标识符作用 挂载于证书域下
	 */
	private void cacheToDiscIfNecssary(String wholeName,String wholePath,String organizationId) {

		File fd=new File(wholePath);
		if(!fd.exists()){       // 文件夹存在则文件存在
			Wrapper<MarketingWxMerchantsExt> queryWapper = new QueryWrapper<>();
			((QueryWrapper<MarketingWxMerchantsExt>) queryWapper).eq("organizationId",organizationId);
			MarketingWxMerchantsExt marketingWxMerchantsExt = marketingWxMerchantsExtMapper.selectOne(queryWapper);
			Asserts.check(marketingWxMerchantsExt!=null && marketingWxMerchantsExt.getCertificateInfo() !=null ,"证书获取失败");
			try {
				fd.mkdirs();
				FileOutputStream fileOutputStream = new FileOutputStream(wholeName);
				fileOutputStream.write(marketingWxMerchantsExt.getCertificateInfo());
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("存储证书到本地磁盘失败");
			}
		}
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
