package com.jgw.supercodeplatform.marketing.weixinpay;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class WXPayTradeNoGenerator {
	public static int code = 0;
	public static SimpleDateFormat formart = new SimpleDateFormat("yyyyMMddHHmmssSSS");


	@Autowired
	RedisLockUtil lock;

	public String tradeNo() throws SuperCodeException{
		boolean acquireLock = false;
		acquireLock = lock.lock("lock:wxtrade:busizess",5000,5,200);
		if(acquireLock){
			if (code > 99999) {
				code = 0;
			}
			code++;
		}else{
			throw new SuperCodeException("获取微信订单锁失败...");
		}

		if(acquireLock){
			try{
				// lua脚本
				lock.releaseLock("lock:wxtrade:busizess");
			}catch (Exception e){
 				e.printStackTrace();
			}
		}
		String time = formart.format(new Date());
		return time + code;
	}
	

}
