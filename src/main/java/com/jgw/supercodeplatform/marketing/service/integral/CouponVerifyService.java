package com.jgw.supercodeplatform.marketing.service.integral;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.coupon.MarketingMemberCouponMapperExt;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingCouponPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;

@Service
public class CouponVerifyService extends AbstractPageService<MarketingCouponPageParam>{

	@Autowired
	private MarketingMemberCouponMapperExt marketingMemberCouponMapper;
	
	@Override
	protected List<MarketingMemberCoupon> searchResult(MarketingCouponPageParam searchParams) throws Exception {
		List<MarketingMemberCoupon> marketingMemberCouponList = marketingMemberCouponMapper.list(searchParams);
		return marketingMemberCouponList;
	}

	@Override
	protected int count(MarketingCouponPageParam searchParams) throws Exception {
		return marketingMemberCouponMapper.count(searchParams);
	}
	
	public List<MarketingMemberCoupon> searchVerfiyList(MarketingCouponPageParam marketingCouponPageParam){
		return marketingMemberCouponMapper.searchVerfiyList(marketingCouponPageParam);
	}
	
}
