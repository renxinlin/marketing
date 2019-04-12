package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRuleProductService;
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
	private MarketingMembersService memberService;

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
		MarketingMembers members = null;
		if (StringUtils.isBlank(openId)) {
			if (null == memberId) {
				result.setState(500);
				result.setMsg("openid为空，memberId不能为空");
				return result;
			}
			members = memberService.getMemberById(memberId);
			if (null == members) {
				result.setState(500);
				result.setMsg("无此用户");
				return result;
			}
			// 2.校验手机验证码是否正确
			String redisPhoneCode = redisUtil.get(RedisKey.phone_code_prefix + mobile);
			if (StringUtils.isBlank(redisPhoneCode)) {
				result.setState(500);
				result.setMsg("验证码不存在或已过期请重新获取验证码");
				return result;
			}

			if (!redisPhoneCode.equals(verificationCode)) {
				result.setState(500);
				result.setMsg("验证码不正确");
				return result;
			}

			// 3.校验用户的手机号是否为mobile参数
			String phone = members.getMobile();
			if (StringUtils.isBlank(phone) || !phone.equals(mobile)) {
				result.setState(500);
				result.setMsg("手机号不属于当前用户");
				return result;
			}
		} else {
			members = memberService.selectByOpenIdAndOrgId(openId, organizationId);
			if (null == members) {
				result.setState(500);
				result.setMsg("无此用户");
				return result;
			}
		}

		// 4.根据产品id查询当前产品是否参与积分
		IntegralRuleProduct inRuleProduct = ruleProductService.selectByProductIdAndOrgId(productId, organizationId);
		if (null == inRuleProduct) {
			result.setState(500);
			result.setMsg("该产品对应的码未参与积分");
			return result;
		}
		// 5.查询ES中当前码和码制的积分是否被领取
		  String nowTime=staticESSafeFormat.format(new Date());
		 Long num= esService.countCodeIntegral(outerCodeId,codeTypeId);
		 if (null!=num && num.intValue()>=1) {
			 result.setState(500);
			 result.setMsg("该码积分已被领取");
			 return result;
		 }
		 
		// 6.判断当前用户是否符合积分设置规则的那些条件，符合就给对应积分

		// 7.把当前码存入积分ES。注意6,7是一个事务保证一致性且需在redis的同步锁里以防多个用户同时操作
		result.setState(200);
		return result;
	}

}
