package com.jgw.supercodeplatform.marketing.asyntask;

import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.enums.market.SalerAmountStatusEnum;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPay;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
/**
 * 异步发送微信零钱任务
 * @author czm
 *
 */
public class WXPayAsynTask implements Runnable{
	protected static Logger logger = LoggerFactory.getLogger(WXPayAsynTask.class);
    private WXPay wxPay;
    private String urlSuffix;
    private Map<String, String> reqData;
    private int connectTimeoutMs,  readTimeoutMs;
    
	public WXPayAsynTask(WXPay wxPay, String urlSuffix, Map<String, String> reqData, int connectTimeoutMs,
			int readTimeoutMs) {
		this.wxPay = wxPay;
		this.urlSuffix = urlSuffix;
		this.reqData = reqData;
		this.connectTimeoutMs = connectTimeoutMs;
		this.readTimeoutMs = readTimeoutMs;
	}

	@Override
	public void run() {
		try {
			String partner_trade_no=reqData.get("partner_trade_no");
			if (StringUtils.isBlank(partner_trade_no)) {
				logger.error("支付时订单号partner_trade_no为空未能发起支付");
			}
			WXPayTradeOrderMapper wxTradeNoMapper=SpringContextUtil.getBean(WXPayTradeOrderMapper.class);
			IntegralRecordMapperExt integralRecordMapper=SpringContextUtil.getBean(IntegralRecordMapperExt.class);
			WXPayTradeOrder wXTradeNo=wxTradeNoMapper.selectByTradeNo(partner_trade_no);
			if (null==wXTradeNo) {
				logger.error("根据订单号partner_trade_no="+partner_trade_no+" 无法查询到订单");
				return ;
			}
			
			if (wXTradeNo.getTradeStatus().equals((byte)1)) {
				logger.error("根据订单号partner_trade_no="+partner_trade_no+" 查询到订单状态已经是支付成功，");
				return ;
			}
			wXTradeNo.setTradeStatus((byte)2);
			String result=wxPay.requestWithCert(urlSuffix, reqData, connectTimeoutMs, readTimeoutMs);
			logger.info("发起支付后返回数据为："+result);
			Map<String,String> mapResult=WXPayUtil.xmlToMap(result);
			String return_code=mapResult.get("return_code");
			String return_msg=mapResult.get("return_msg");
			wXTradeNo.setReturnCode(return_code);
			wXTradeNo.setReturnMsg(return_msg);
			String status = SalerAmountStatusEnum.SEND_FAIL.status;
			//先判断return_code和return_msg
			if ("SUCCESS".equals(return_code) ) {
				if (StringUtils.isBlank(return_msg)) {
					String result_code=mapResult.get("result_code");
					//再判断result_code
					if ("SUCCESS".equals(result_code)) {
						wXTradeNo.setTradeStatus((byte)1);
						//保存中奖纪录
						status = SalerAmountStatusEnum.SEND_SUCCESS.status;
					}else if ("FAIL".equals(result_code)) {
						wXTradeNo.setTradeStatus((byte)2);
						String err_code=mapResult.get("err_code");
						String err_code_des=mapResult.get("err_code_des");
						wXTradeNo.setErrCode(err_code);
						wXTradeNo.setErrCodeDes(err_code_des);
						//为SYSTEMERROR错误时使用原单号重试
						if ("SYSTEMERROR".equals(err_code)) {

						}
					}
				}else {
					wXTradeNo.setTradeStatus((byte)2);
					wXTradeNo.setErrCodeDes("签名失败");
				}
			}else {
				wXTradeNo.setTradeStatus((byte)2);
			}
			//更新导购红包
			integralRecordMapper.updateSalerPrizeRecord(status,wXTradeNo.getWinningCode(),wXTradeNo.getOrganizationId());
			wxTradeNoMapper.update(wXTradeNo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
