package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.constants.SystemLabelEnum;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberProductIntegral;
import com.jgw.supercodeplatform.marketing.service.activity.coupon.MarketingMemberProductIntegralService;
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
	protected static Logger logger = LoggerFactory.getLogger(IntegralFrontController.class);
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
	private RedisLockUtil lockUtil;
	
	@Autowired
	private MarketingMemberProductIntegralService productIntegralService;
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
	@ApiImplicitParams(value = { @ApiImplicitParam(paramType = "query", value = "码", name = "outerCodeId",required=true),
			@ApiImplicitParam(paramType = "query", value = "码制", name = "codeTypeId",required=true),
			@ApiImplicitParam(paramType = "query", value = "产品id", name = "productId",required=true),
			@ApiImplicitParam(paramType = "query", value = "产品批次id", name = "productBatchId",required=true),
			@ApiImplicitParam(paramType = "query", value = "产品批次id", name = "memberId",required=true)
		})
	public RestResult<List<String>> receive(@RequestParam(name = "outerCodeId") String outerCodeId,
			@RequestParam(name = "codeTypeId") String codeTypeId,
			@RequestParam(name = "productId") String productId,
			@RequestParam(name = "productBatchId") String productBatchId,
			@RequestParam(name = "memberId", required = true) Long memberId)
			throws SuperCodeException, ParseException {
		RestResult<List<String>> result = new RestResult<List<String>>();
		// 1.如果openid不为空那根据openid和组织id查用户，否则肯定是进行了手机登录那就必须传手机号验证码和用户主键id
		logger.info("领取积分获取到参数codeTypeId="+codeTypeId+",productId="+productId+",productBatchId="+productBatchId+",memberId="+memberId);
		MarketingMembers members = memberService.getMemberById(memberId);
		if (null==members) {
			throw new SuperCodeException("用户不存在", 500);
		}
		String organizationId=members.getOrganizationId();
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
		
		Long marketingcodeTypeId=SystemLabelEnum.MARKETING.getCodeTypeId();
		if (!marketingcodeTypeId.equals(Long.parseLong(codeTypeId))) {
			result.setState(500);
			result.setMsg("该码不是营销码无法参与积分");
			return result;
		}
		
		// 5.查询ES中当前码和码制的积分是否被领取
		String nowTime=null;
		long scanCodeLongTime=0L;
		synchronized (this) {
			nowTime=staticESSafeFormat.format(new Date());
			scanCodeLongTime=staticESSafeFormat.parse(nowTime).getTime();
		}
		
		List<String> dataList=new ArrayList<String>();
		Map<String, Object> data=calculateReceiveIntegral(outerCodeId,codeTypeId,productId,inRuleProduct.getProductName(),organizationId,members, integralRule, nowTime,inRuleProduct,scanCodeLongTime,dataList);
		
		Integer haveIntegral=members.getHaveIntegral()==null?0:members.getHaveIntegral();
		boolean acquireLock = lockUtil.lock("intrgral:" + outerCodeId + ":" + codeTypeId,5000,5,200);
		if (acquireLock) {
			Long num= esService.countCodeIntegral(outerCodeId,codeTypeId);
			if (null!=num && num.intValue()>=1) {
				result.setState(500);
				result.setMsg("该码积分已被领取");
				return result;
			}
			Integer sum=(Integer)data.get("integralSum");
		    members.setHaveIntegral(haveIntegral+sum);
		    members.setIntegralReceiveDate(new Date());
		    memberService.update(members);
		    List<IntegralRecord> inRecords= (List<IntegralRecord>) data.get("integralRecords");
		    if (null!=inRecords && !inRecords.isEmpty()) {
		    	integralRecordService.batchInsert(inRecords);
		    }
		    result.setResults(dataList);
		    //添加产品积分记录并获取优惠券
		    if(sum != null && sum.longValue() > 0) {
		    	MarketingMemberProductIntegral productIntegral = new MarketingMemberProductIntegral();
		    	productIntegral.setAccrueIntegral(sum.longValue());
		    	productIntegral.setMemberId(memberId);
		    	productIntegral.setOrganizationId(organizationId);
		    	productIntegral.setProductBatchId(productBatchId);
		    	productIntegral.setProductId(productId);
		    	productIntegralService.obtainCoupon(productIntegral, members, inRuleProduct.getProductName(),outerCodeId);
		    }
			// 7.把当前码存入积分ES。注意6,7是一个事务保证一致性且需在redis的同步锁里以防多个用户同时操作
			esService.addCodeIntegral(members.getId(), outerCodeId, codeTypeId, productId, productBatchId, organizationId, staticESSafeFormat.parse(nowTime).getTime());
		}else {
			result.setState(500);
			result.setMsg("扫码人数过多请稍后再试");
			return result;
		}

		result.setState(200);
		return result;
	}
   
	private Map<String, Object> calculateReceiveIntegral(String outerCodeId,String codeTypeId,String productId,String productName,String organizationId,MarketingMembers members, IntegralRule integralRule, String nowTime, IntegralRuleProduct inRuleProduct,  long scanCodeTimeLong,List<String> dataList ) {
		 int integralSum=0;
		 Map<String, Object> data=new HashMap<String, Object>();
         Byte rewardRule=inRuleProduct.getRewardRule();
         List<IntegralRecord> inRecords=new ArrayList<IntegralRecord>();
         //如果直接按产品
         if (rewardRule.intValue()==0) {
        	 Integer rewardIntegral=inRuleProduct.getRewardIntegral();
    		 IntegralRecord integralRecord = newIntegralRecord(outerCodeId, codeTypeId, productId, productName,
					organizationId,rewardIntegral,IntegralReasonEnum.PRODUCT_INTEGRAL.getIntegralReasonCode(),IntegralReasonEnum.PRODUCT_INTEGRAL.getIntegralReason(), members);
    		 inRecords.add(integralRecord);
    		 dataList.add("扫码获取产品奖励+"+rewardIntegral+"积分");
        	 integralSum+=inRuleProduct.getRewardIntegral();
         //如果按照
		 }else if(rewardRule.intValue()==1) {
			 Integer productIntegral=(int) ((inRuleProduct.getProductPrice()/inRuleProduct.getPerConsume())*inRuleProduct.getRewardIntegral());
			 integralSum+=productIntegral;
			 
	  		 IntegralRecord integralRecord = newIntegralRecord(outerCodeId, codeTypeId, productId, productName,
						organizationId,productIntegral,IntegralReasonEnum.PRODUCT_INTEGRAL.getIntegralReasonCode(),IntegralReasonEnum.EXCHANGE_PRODUCT.getIntegralReason(), members);
	    		
	  		dataList.add("恭喜领取+"+productIntegral+"积分");
	  		 inRecords.add(integralRecord);
		 }

         String birthDay=members.getBirthday();
         Byte birthdayStatus =integralRule.getIntegralByBirthdayStatus();		 
         Byte firstTimeStatus =integralRule.getIntegralByFirstTimeStatus();
         if (null!=birthdayStatus && birthdayStatus.intValue()==1) {
			if (nowTime.equals(birthDay)) {
		         logger.info("查询历史首次scanCodeTimeLong="+scanCodeTimeLong);
				Long num=esService.countIntegralByUserIdAndDate(members.getId(), scanCodeTimeLong,organizationId);
				if (null==num || num.intValue()==0) {
					Integer integralByBirthday=integralRule.getIntegralByBirthday();
					 IntegralRecord integralRecord = newIntegralRecord(outerCodeId, codeTypeId, productId, productName,
		     					organizationId,integralByBirthday,IntegralReasonEnum.BIRTHDAY.getIntegralReasonCode(),IntegralReasonEnum.BIRTHDAY.getIntegralReason(), members);
					dataList.add("生日快乐，额外献上+"+integralByBirthday+"积分");
					integralSum+=integralByBirthday;
					inRecords.add(integralRecord);
				}
			}
		 }
         
         if (null!=firstTimeStatus && firstTimeStatus.intValue()==1) {
        	 List<IntegralRecord> integralRecords=integralRecordService.selectByMemberIdAndIntegralReasonCode(members.getId(),IntegralReasonEnum.FIRST_INTEGRAL.getIntegralReasonCode(),organizationId);
        	 if (null==integralRecords || integralRecords.isEmpty()) {
        		 Integer firstReceiveNum=integralRule.getIntegralByFirstTime();
        		 IntegralRecord integralRecord = newIntegralRecord(outerCodeId, codeTypeId, productId, productName,
     					organizationId,firstReceiveNum,IntegralReasonEnum.FIRST_INTEGRAL.getIntegralReasonCode(),IntegralReasonEnum.FIRST_INTEGRAL.getIntegralReason(), members);
        		 inRecords.add(integralRecord);
        		 dataList.add("首次领取，额外献上+"+firstReceiveNum+"积分");
        		 //总分加上
				integralSum+=integralRule.getIntegralByFirstTime();
			 }
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

}
