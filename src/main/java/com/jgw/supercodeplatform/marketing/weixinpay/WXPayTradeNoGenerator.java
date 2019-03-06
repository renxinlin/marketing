package com.jgw.supercodeplatform.marketing.weixinpay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WXPayTradeNoGenerator {
	public static int code = 0;
	public static SimpleDateFormat formart = new SimpleDateFormat("yyyyMMddHHmmss");

	public static synchronized String tradeNo() {
		if (code > 99999) {
			code = 0;
		}
		code++;
		String time = formart.format(new Date());
		return time + code;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			String no=tradeNo();
			System.out.println(no);
		}
	}
}
