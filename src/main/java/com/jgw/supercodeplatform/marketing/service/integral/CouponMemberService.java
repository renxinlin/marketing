package com.jgw.supercodeplatform.marketing.service.integral;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.coupon.MarketingMemberCouponMapperExt;
import com.jgw.supercodeplatform.marketing.dto.coupon.CouponPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;
import com.jgw.supercodeplatform.marketing.vo.coupon.CouponPageVo;

@Service
public class CouponMemberService extends AbstractPageService<CouponPageParam> {

	@Autowired
	private MarketingMemberCouponMapperExt marketingMemberCouponMapper;
	
	@Override
	protected List<CouponPageVo> searchResult(CouponPageParam searchParams) throws Exception {
		return marketingMemberCouponMapper.listCoupon(searchParams);
	}

	@Override
	protected int count(CouponPageParam searchParams) throws Exception {
		return marketingMemberCouponMapper.couponCount(searchParams);
	}
	
	public MarketingMemberCoupon getMarketingMemberCouponByCouponCode(String couponCode) {
		return marketingMemberCouponMapper.getMarketingMemberCouponByCouponCode(couponCode);
	}
	
	public void verifyCoupon(MarketingMemberCoupon marketingMemberCoupon) {
		marketingMemberCouponMapper.updateByPrimaryKeySelective(marketingMemberCoupon);
	}
	
}
