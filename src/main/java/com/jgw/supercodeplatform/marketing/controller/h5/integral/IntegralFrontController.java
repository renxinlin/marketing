package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRuleProductService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRuleService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 积分记录controller
 *
 */
@RestController
@RequestMapping("/marketing/front/integral")
@Api(tags = "积分h5")
public class IntegralFrontController {
	
	//需加锁
	private static SimpleDateFormat staticESSafeFormat=new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private IntegralRuleProductService ruleProductService;

	@Autowired
	private IntegralRuleService ruleService;
	
	@Autowired
	private MarketingMembersService memberService;

	@Autowired
	private IntegralRecordService integralRecordService;
	
	@Autowired
	private CodeEsService esService;
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 领取积分
	 * 
	 * @param
	 * @return
	 * @throws SuperCodeException
	 * @throws ParseException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/receive", method = RequestMethod.GET)
	@ApiOperation(value = "积分领取", notes = "")
	@ApiImplicitParams(value = { @ApiImplicitParam(paramType = "query", value = "码", name = "outerCodeId"),
			@ApiImplicitParam(paramType = "query", value = "码制", name = "codeTypeId"),
			@ApiImplicitParam(paramType = "query", value = "产品id", name = "productId"),
			@ApiImplicitParam(paramType = "query", value = "产品批次id", name = "productBatchId"),
			@ApiImplicitParam(paramType = "query", value = "产品批次id", name = "memberId"),
			@ApiImplicitParam(paramType = "query", value = "登录手机号", name = "mobile"),
			@ApiImplicitParam(paramType = "query", value = "微信openid", name = "openId"),
			@ApiImplicitParam(paramType = "query", value = "组织id", name = "organizationId"),
			@ApiImplicitParam(paramType = "query", value = "登录验证码", name = "verificationCode") })
	public RestResult<String> receive(@RequestParam(name = "outerCodeId") String outerCodeId,
			@RequestParam(name = "codeTypeId") String codeTypeId, @RequestParam(name = "productId") String productId,
			@RequestParam(name = "productBatchId") String productBatchId,
			@RequestParam(name = "memberId", required = false) Long memberId,
			@RequestParam(name = "mobile", required = false) String mobile,
			@RequestParam(name = "openId") String openId, @RequestParam(name = "organizationId") String organizationId,
			@RequestParam(name = "verificationCode", required = false) String verificationCode)
			throws SuperCodeException, ParseException {
		RestResult<String> result = new RestResult<String>();
		// 1.如果openid不为空那根据openid和组织id查用户，否则肯定是进行了手机登录那就必须传手机号验证码和用户主键id
		MarketingMembers members = getUser(memberId, mobile, openId, organizationId, verificationCode);
		
		// 6.判断当前用户是否符合积分设置规则的那些条件，符合就给对应积分
		 IntegralRule integralRule=ruleService.selectByOrgId(organizationId);
		 if (null == integralRule) {
			result.setState(500);
			result.setMsg("当前企业未设置积分规则");
			return result;
		 }
		// 4.根据产品id查询当前产品是否参与积分
		IntegralRuleProduct inRuleProduct = ruleProductService.selectByProductIdAndOrgId(productId, organizationId);
		if (null == inRuleProduct) {
			result.setState(500);
			result.setMsg("该产品对应的码未参与积分");
			return result;
		}
		// 5.查询ES中当前码和码制的积分是否被领取
		String nowTime=null;
		synchronized (this) {
			nowTime=staticESSafeFormat.format(new Date());
		}
		Map<String, Object> data=calculateReceiveIntegral(outerCodeId,codeTypeId,productId,null,organizationId,members, integralRule, nowTime,inRuleProduct);
		Integer haveIntegral=members.getHaveIntegral()==null?0:members.getHaveIntegral();
		synchronized (this) {
			Long num= esService.countCodeIntegral(outerCodeId,codeTypeId);
			if (null!=num && num.intValue()>=1) {
				result.setState(500);
				result.setMsg("该码积分已被领取");
				return result;
			}
			// 7.把当前码存入积分ES。注意6,7是一个事务保证一致性且需在redis的同步锁里以防多个用户同时操作
			esService.addCodeIntegral(members.getId(), outerCodeId, codeTypeId, productId, productBatchId, organizationId, staticESSafeFormat.parse(nowTime));
		}
		  members.setHaveIntegral(haveIntegral+(Integer)data.get("integralSum"));
		  memberService.update(members);
		  List<IntegralRecord> inRecords= (List<IntegralRecord>) data.get("integralRecords");
		  if (null!=inRecords && !inRecords.isEmpty()) {
			  integralRecordService.batchInsert(inRecords);
		  }
		result.setState(200);
		return result;
	}
   
	private Map<String, Object> calculateReceiveIntegral(String outerCodeId,String codeTypeId,String productId,String productName,String organizationId,MarketingMembers members, IntegralRule integralRule, String nowTime, IntegralRuleProduct inRuleProduct ) {
		 int integralSum=0;
		 Map<String, Object> data=new HashMap<String, Object>();
		 
         String birthDay=members.getBirthday();
         Byte birthdayStatus =integralRule.getIntegralByBirthdayStatus();		 
         Byte firstTimeStatus =integralRule.getIntegralByFirstTimeStatus();
         if (null!=birthdayStatus && birthdayStatus.intValue()==1) {
			if (nowTime.equals(birthDay)) {
				integralSum+=integralRule.getIntegralByBirthday();
			}
		 }
         
         List<IntegralRecord> inRecords=new ArrayList<IntegralRecord>();
         if (null!=firstTimeStatus && firstTimeStatus.intValue()==1) {
        	 List<IntegralRecord> integralRecords=integralRecordService.selectByMemberIdAndIntegralReasonCode(members.getId(),1);
        	 if (null==integralRecords || integralRecords.isEmpty()) {
        		 IntegralRecord integralRecord = newIntegralRecord(outerCodeId, codeTypeId, productId, productName,
     					organizationId,integralRule.getIntegralByFirstTime(),IntegralReasonEnum.FIRST_INTEGRAL.getIntegralReasonCode(),IntegralReasonEnum.FIRST_INTEGRAL.getIntegralReason(), members);
        		 
        		 inRecords.add(integralRecord);
        		 //总分加上
				integralSum+=integralRule.getIntegralByFirstTime();
			 }
		 }
         Byte rewardRule=inRuleProduct.getRewardRule();
         //如果直接按产品
         if (rewardRule.intValue()==0) {
    		 IntegralRecord integralRecord = newIntegralRecord(outerCodeId, codeTypeId, productId, productName,
					organizationId,inRuleProduct.getRewardIntegral(),IntegralReasonEnum.EXCHANGE_PRODUCT.getIntegralReasonCode(),IntegralReasonEnum.EXCHANGE_PRODUCT.getIntegralReason(), members);
    		 inRecords.add(integralRecord);
    		 
        	 integralSum+=inRuleProduct.getRewardIntegral();
         //如果按照
		 }else if(rewardRule.intValue()==1) {
			 Integer productIntegral=(int) ((inRuleProduct.getProductPrice()/inRuleProduct.getPerConsume())*inRuleProduct.getRewardIntegral());
			 integralSum+=productIntegral;
			 
	  		 IntegralRecord integralRecord = newIntegralRecord(outerCodeId, codeTypeId, productId, productName,
						organizationId,productIntegral,IntegralReasonEnum.EXCHANGE_PRODUCT.getIntegralReasonCode(),IntegralReasonEnum.EXCHANGE_PRODUCT.getIntegralReason(), members);
	    		 inRecords.add(integralRecord);
		 }
         data.put("integralSum", integralSum);
         data.put("integralRecords", inRecords);
         return data;
	}

	private IntegralRecord newIntegralRecord(String outerCodeId, String codeTypeId, String productId,
			String productName, String organizationId,Integer integralNum,Integer integralCode,String integralCodeReason, MarketingMembers members) {
		 IntegralRecord integralRecord=new IntegralRecord();
		 integralRecord.setIntegralNum(integralNum);
		 integralRecord.setOuterCodeId(outerCodeId);
		 integralRecord.setCodeTypeId(codeTypeId);
		 integralRecord.setIntegralReasonCode(integralCode);
		 integralRecord.setIntegralReason(integralCodeReason);
		 integralRecord.setCustomerId(members.getCustomerId());
		 integralRecord.setCustomerName(members.getCustomerName());
		 integralRecord.setMemberId(members.getId());
		 integralRecord.setMemberName(members.getUserName());
		 integralRecord.setMemberType(members.getMemberType());
		 integralRecord.setMobile(members.getMobile());
		 integralRecord.setOrganizationId(organizationId);
		 integralRecord.setProductId(productId);
		 integralRecord.setProductName(productName);
		 integralRecord.setIntegralType(0);
		return integralRecord;
	}

	private MarketingMembers getUser(Long memberId, String mobile, String openId, String organizationId,
			String verificationCode) throws SuperCodeException {
		MarketingMembers members = null;
		if (StringUtils.isBlank(openId)) {
			if (null == memberId) {
				throw new SuperCodeException("openid为空，memberId不能为空", 500);
			}
			members = memberService.getMemberById(memberId);
			if (null == members) {
				throw new SuperCodeException("根据memberId="+memberId+"无法查询到用户", 500);
			}
			// 2.校验手机验证码是否正确
			String redisPhoneCode = redisUtil.get(RedisKey.phone_code_prefix + mobile);
			if (StringUtils.isBlank(redisPhoneCode)) {
				throw new SuperCodeException("验证码不存在或已过期请重新获取验证码", 500);
			}

			if (!redisPhoneCode.equals(verificationCode)) {
				throw new SuperCodeException("验证码不正确", 500);
			}

			// 3.校验用户的手机号是否为mobile参数
			String phone = members.getMobile();
			if (StringUtils.isBlank(phone) || !phone.equals(mobile)) {
				throw new SuperCodeException("手机号不属于当前用户", 500);
			}
		} else {
			members = memberService.selectByOpenIdAndOrgId(openId, organizationId);
			if (null == members) {
				throw new SuperCodeException("根据openid="+openId+",organizationId="+organizationId+"无法查询到用户", 500);
			}
		}
		return members;
	}

}
