package com.jgw.supercodeplatform.marketing.service.integral;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.coupon.MarketingMemberCouponMapperExt;
import com.jgw.supercodeplatform.marketing.dto.coupon.CouponCustmerVerifyPageParam;
import com.jgw.supercodeplatform.marketing.vo.coupon.CouponVerifyVo;

@Service
public class CouponCustmerVerifyService extends AbstractPageService<CouponCustmerVerifyPageParam> {

	@Autowired
	private MarketingMemberCouponMapperExt marketingMemberCouponMapper;
	
	@Override
	protected List<CouponVerifyVo> searchResult(CouponCustmerVerifyPageParam searchParams) throws Exception {
		List<CouponVerifyVo> cpList = marketingMemberCouponMapper.listCustomerVerifyCoupon(searchParams);
		if (cpList == null) {
			cpList = new ArrayList<>();
		}
		return cpList;
	}

	@Override
	protected int count(CouponCustmerVerifyPageParam searchParams) throws Exception {
		return marketingMemberCouponMapper.CustomerVerifyCouponCout(searchParams);
	}

	
	
}
