package com.jgw.supercodeplatform.marketing.dao.weixin;

import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeNo;

@Mapper
public interface WXPayTradeNoMapper {

	WXPayTradeNo selectByTradeNo(String partner_trade_no);

}
