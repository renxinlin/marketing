package com.jgw.supercodeplatform.marketing.dao.weixin;

import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;

@Mapper
public interface WXPayTradeOrderMapper {

	WXPayTradeOrder selectByTradeNo(String partner_trade_no);

	int insert(WXPayTradeOrder tradeOrder);

}
