package com.jgw.supercodeplatform.marketing.controller.h5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingReceivingPageService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
 * 领取页controller
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/front/receivingPag")
@Api(tags = "h5页面获取领取页信息")
public class MarketingReceivingPageFrontController {
	protected static Logger logger = LoggerFactory.getLogger(MarketingReceivingPageFrontController.class);
	@Autowired
	private MarketingReceivingPageService service;
	
	@Autowired
	private MarketingWxMerchantsService wxMerchantsService;
	
	@RequestMapping(value = "/getByAsId",method = RequestMethod.GET)
    @ApiOperation(value = "根据活动设置id获取领取页记录，扫码时可以通过该接口获取是否需要领取页", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "当前扫码唯一id",name="wxstate",required=true),@ApiImplicitParam(paramType="query",value = "获取设置主键id",name="activitySetId",required=false)})
	public RestResult<MarketingReceivingPage> getByAsId(String wxstate,Long activitySetId) throws Exception{
		ScanCodeInfoMO scInfoMO=GlobalRamCache.scanCodeInfoMap.get(wxstate);
		if (null==activitySetId) {
			if (null==scInfoMO) {
				throw new SuperCodeException("授权回调方法无法根据state="+wxstate+"获取到用户扫码缓存信息请重试", 500);
			}
			activitySetId=scInfoMO.getActivitySetId();
		}
		MarketingReceivingPage mReceivingPage=service.selectByActivitySetId(activitySetId);
		if (null==mReceivingPage) {
			throw new SuperCodeException("h5扫码时获取领取页信息失败根据activitySetId="+activitySetId+"无法获取领取页信息", 500);
		}
		if (null!=mReceivingPage.getIsQrcodeView() && 1==mReceivingPage.getIsQrcodeView().intValue()) {
			String organizationId=scInfoMO.getOrganizationId();
			MarketingWxMerchants mWxMerchants=wxMerchantsService.selectByOrganizationId(organizationId);
			if (null==mWxMerchants) {
				throw new SuperCodeException("h5扫码时获取领取页信息时校验是否关注过微信公众号获取微信公众号信息失败organizationId="+organizationId+"无法获取信息", 500);
			}
			try {
				boolean flag=judgeUserSubsribeGZH(mWxMerchants.getMchAppid(), mWxMerchants.getMerchantSecret(), scInfoMO.getOpenId());
				logger.info("获取判断是否关注公众号方法返回值="+flag);
				if (flag) {
					mReceivingPage.setIsQrcodeView((byte)0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		RestResult<MarketingReceivingPage> restResult=new RestResult<MarketingReceivingPage>();
		restResult.setState(200);
		restResult.setResults(mReceivingPage);
		restResult.setMsg("成功");
		return restResult;
	}
	
	
	public static void main(String[] args) throws Exception {
		judgeUserSubsribeGZH("wx32ab5628a5951ecc", "e3fb09c9126cd8bc12399e56a35162c4", "oeVn5sq-wk7_MH4jN2BUQ_fSRv-A");
	}
	public static boolean  judgeUserSubsribeGZH(String appId,String sercert,String openId) throws Exception {
		logger.info("判断是否关注过公众号方法参数appId="+appId+",sercert="+sercert+",openId="+openId);
		HttpClientResult reHttpClientResult=HttpRequestUtil.doGet(WechatConstants.ACCESS_TOKEN_URL+"&appid="+appId+"&secret="+sercert);
	    String body=reHttpClientResult.getContent();
	    logger.info("判断是否关注过公众号方法获取acessetoken返回结果="+body);
	    if (body.contains("access_token")) {
			JSONObject tokenObj=JSONObject.parseObject(body);
			String token=tokenObj.getString("access_token");
			HttpClientResult userInfoResult=HttpRequestUtil.doGet(WechatConstants.WECHAT_USER_INFO+"?access_token="+token+"&openid="+openId+"&lang=zh_CN");
			String userInfoBody=userInfoResult.getContent();
			logger.info("判断是否关注过公众号方法获取用户基本信息`返回结果="+userInfoBody);
			if (userInfoBody.contains("subscribe")) {
				JSONObject userObj=JSONObject.parseObject(userInfoBody);
				int subscribe=userObj.getInteger("subscribe");
				if (1==subscribe) {
					//如果已关注过则不需要再关注
					return true;
				}
			}
		}
		return false;
	}
}
