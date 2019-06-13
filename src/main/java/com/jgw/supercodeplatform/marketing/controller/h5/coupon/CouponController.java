package com.jgw.supercodeplatform.marketing.controller.h5.coupon;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.dto.coupon.CouponCustmerVerifyPageParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.CouponPageParam;
import com.jgw.supercodeplatform.marketing.enums.market.CouponVerifyEnum;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;
import com.jgw.supercodeplatform.marketing.service.integral.CouponCustmerVerifyService;
import com.jgw.supercodeplatform.marketing.service.integral.CouponMemberService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketing.vo.coupon.CouponPageVo;
import com.jgw.supercodeplatform.marketing.vo.coupon.CouponVerifyVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/marketing/front/coupon")
@Api(tags = "抵扣券H5")
public class CouponController {
	
	@Autowired
	private CouponMemberService couponMemberService;
	@Autowired
	private CouponCustmerVerifyService couponCustmerVerifyService;
	
	@GetMapping("/listCoupon")
	@ApiOperation("抵扣券记录")
	@ApiImplicitParams(@ApiImplicitParam(paramType="header",value = "新平台token",name="super-token"))
	public RestResult<PageResults<List<CouponPageVo>>> listCoupon(CouponPageParam couponPageParam, @ApiIgnore H5LoginVO jwtUser) throws Exception{
		couponPageParam.setMemberId(jwtUser.getMemberId());
		PageResults<List<CouponPageVo>> couponPageResult = couponMemberService.listSearchViewLike(couponPageParam);
		return new RestResult<>(HttpStatus.SC_OK, "查询成功", couponPageResult);
	}
	
	@GetMapping("/couponVerify")
	@ApiOperation("抵扣券核销")
	@ApiImplicitParams({@ApiImplicitParam(paramType="header",value = "新平台token",name="super-token")
	,@ApiImplicitParam(paramType="body",value = "用户会员手机号",name="memberPhone")
	,@ApiImplicitParam(paramType="body",value = "抵扣券码",name="couponCode")})
	public RestResult<Double> couponVerify(@RequestParam String memberPhone, @RequestParam String couponCode, @ApiIgnore H5LoginVO jwtUser) throws SuperCodeException{
		MarketingMemberCoupon marketingMemberCoupon = couponMemberService.getMarketingMemberCouponByCouponCode(couponCode);
		if(marketingMemberCoupon == null)
			throw new SuperCodeException("‘抵扣券不存在’（输入错误）", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		String phone = marketingMemberCoupon.getMemberPhone();
		if(!memberPhone.equals(phone))
			throw new SuperCodeException("‘会员手机号与抵扣券不匹配’", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		Byte used = marketingMemberCoupon.getUsed();
		if(used != null && used.intValue() == 1)
			throw new SuperCodeException("‘抵扣券已被使用’", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		long currentMills = System.currentTimeMillis();
		Date deductionStartDate = marketingMemberCoupon.getDeductionStartDate();
		if(deductionStartDate != null && deductionStartDate.getTime() > currentMills)
			throw new SuperCodeException("‘抵扣券无法使用’ （没到抵扣时间）", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		Date deductionEndDate = marketingMemberCoupon.getDeductionEndDate();
		if(deductionEndDate != null && deductionEndDate.getTime() < currentMills)
			throw new SuperCodeException("‘抵扣券已过期’", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		String obtainCustomerId = marketingMemberCoupon.getObtainCustomerId();
		if(!StringUtils.equals(obtainCustomerId, jwtUser.getCustomerId()))
			throw new SuperCodeException("‘抵扣券无法使用’ （不在该门店）", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		//填充抵扣券核销信息
		MarketingMemberCoupon memberCoupon = new MarketingMemberCoupon();
		memberCoupon.setId(marketingMemberCoupon.getId());
		memberCoupon.setVerifyMemberId(jwtUser.getMemberId());
		memberCoupon.setVerifyCustomerId(jwtUser.getCustomerId());
		memberCoupon.setVerifyCustomerName(jwtUser.getCustomerName());
		memberCoupon.setVerifyPersonName(jwtUser.getMemberName());
		memberCoupon.setVerifyPersonPhone(jwtUser.getMobile());
		memberCoupon.setVerifyPersonType(CouponVerifyEnum.SALER.getType());
		memberCoupon.setVerifyTime(new Date());
		memberCoupon.setUsed((byte)1);
		couponMemberService.verifyCoupon(memberCoupon);
		return new RestResult<>(HttpStatus.SC_OK, "核销成功", marketingMemberCoupon.getCouponAmount());
	}
	
	@GetMapping("/listVerify")
	@ApiOperation("核销人员核销列表")
	@ApiImplicitParams(@ApiImplicitParam(paramType="header",value = "新平台token",name="super-token"))
	public RestResult<PageResults<List<CouponVerifyVo>>> listVerify(CouponCustmerVerifyPageParam verifyPageParam, @ApiIgnore H5LoginVO jwtUser) throws Exception{
		verifyPageParam.setVerifyCustomerId(jwtUser.getCustomerId());
		verifyPageParam.setVerifyMemberId(jwtUser.getMemberId());
		PageResults<List<CouponVerifyVo>> verifyResult = couponCustmerVerifyService.listSearchViewLike(verifyPageParam);
		return new RestResult<>(HttpStatus.SC_OK, "查询成功", verifyResult);
	}

}
