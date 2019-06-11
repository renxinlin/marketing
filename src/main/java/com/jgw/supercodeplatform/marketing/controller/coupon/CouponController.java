package com.jgw.supercodeplatform.marketing.controller.coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingCouponPageParam;
import com.jgw.supercodeplatform.marketing.enums.market.CouponAcquireConditionEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.integral.CouponVerifyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/coupon/verify")
@Api(tags = "抵扣券核销")
public class CouponController extends CommonUtil{
	
	private static final Map<String, String> FILED_MAP = new HashMap<>();
	
	static {
		FILED_MAP.put("memberPhone", "会员手机");
		FILED_MAP.put("couponCode", "抵扣券");
		FILED_MAP.put("couponAmount", "抵扣券金额");
		FILED_MAP.put("productName", "获得产品");
		FILED_MAP.put("obtainCustmerName", "获得渠道");
		FILED_MAP.put("obtainCondition", "获得条件");
		FILED_MAP.put("createTime", "获得时间");
		FILED_MAP.put("verifyCustomerName", "核销渠道");
		FILED_MAP.put("verifyPersonName", "核销人员");
		FILED_MAP.put("verifyPersonPhone", "核销人员手机");
		FILED_MAP.put("verifyTime", "核销时间");
	}
	
	@Autowired
	private CouponVerifyService couponVerifyService;
	@Autowired
	private MarketingActivitySetService marketingActivitySetService;
	
	@GetMapping("/list")
	@ApiOperation("核销记录")
	@ApiImplicitParams({@ApiImplicitParam(paramType="header",value = "新平台token",name="super-token")})
	public RestResult<PageResults<List<MarketingMemberCoupon>>> listCouponVerify(@RequestBody MarketingCouponPageParam marketingCouponPageParam) throws Exception{
		marketingCouponPageParam.setOrganizationId(getOrganizationId());
		marketingCouponPageParam.setUsed((byte)1);
		MarketingActivitySet marketingActivitySet = marketingActivitySetService.selectById(marketingCouponPageParam.getActivitySetId());
		String validCondition = marketingActivitySet.getValidCondition();
		if(StringUtils.isBlank(validCondition)) {
			throw new SuperCodeException("活动条件为空", 500);
		}
		PageResults<List<MarketingMemberCoupon>> couponResult = couponVerifyService.listSearchViewLike(marketingCouponPageParam);
		MarketingActivitySetCondition marketingActivitySetCondition = JSON.parseObject(validCondition, MarketingActivitySetCondition.class);
		Byte acquireCondition = marketingActivitySetCondition.getAcquireCondition();
		if(acquireCondition != null) {
			int acquireConditionInt = acquireCondition.intValue();
			List<MarketingMemberCoupon> memberCouponList = couponResult.getList();
			if(memberCouponList != null) {
				memberCouponList.forEach(marketingMemberCoupon -> {
					String condtion = CouponAcquireConditionEnum.getConditionByType(acquireConditionInt);
					if(acquireConditionInt == 2 || acquireConditionInt == 3)
						condtion = condtion + marketingActivitySetCondition.getAcquireConditionIntegral();
					marketingMemberCoupon.setObtainCondition(condtion);
				});
			}
		}
		
		return new RestResult<>(HttpStatus.SC_OK, "查询成功", couponResult);
	}
	
	@GetMapping("/export")
	@ApiOperation("导出核销记录")
	@ApiImplicitParams({@ApiImplicitParam(paramType="header",value = "新平台token",name="super-token")})
	public void exportExcel(@RequestBody MarketingCouponPageParam marketingCouponPageParam) throws SuperCodeException {
		marketingCouponPageParam.setOrganizationId(getOrganizationId());
		marketingCouponPageParam.setUsed((byte)1);
		MarketingActivitySet marketingActivitySet = marketingActivitySetService.selectById(marketingCouponPageParam.getActivitySetId());
		String validCondition = marketingActivitySet.getValidCondition();
		if(StringUtils.isBlank(validCondition)) {
			throw new SuperCodeException("活动条件为空", 500);
		}
		List<MarketingMemberCoupon> marketingMemberCouponList = couponVerifyService.searchVerfiyList(marketingCouponPageParam);
		MarketingActivitySetCondition marketingActivitySetCondition = JSON.parseObject(validCondition, MarketingActivitySetCondition.class);
		Byte acquireCondition = marketingActivitySetCondition.getAcquireCondition();
		if(acquireCondition != null) {
			int acquireConditionInt = acquireCondition.intValue();
			if(marketingMemberCouponList != null) {
				marketingMemberCouponList.forEach(marketingMemberCoupon -> {
					String condtion = CouponAcquireConditionEnum.getConditionByType(acquireConditionInt);
					if(acquireConditionInt == 2 || acquireConditionInt == 3)
						condtion = condtion + marketingActivitySetCondition.getAcquireConditionIntegral();
					marketingMemberCoupon.setObtainCondition(condtion);
				});
			}
		}
		ExcelUtils.listToExcel(marketingMemberCouponList, FILED_MAP, "核销记录", response);
	}
	
}
