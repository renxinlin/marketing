package com.jgw.supercodeplatform.marketing.controller.activity;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingActivityCouponAddParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingActivityCouponUpdateParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.activity.coupon.CouponService;
import com.jgw.supercodeplatform.marketing.service.activity.coupon.CouponUpdateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/activity/coupon")
@Api(tags = "抵扣券管理")
public class MarketingCouponController {
    @Autowired
    private CouponService service;
    @Autowired
    private CouponUpdateService updateService;

    @Autowired
    private MarketingActivitySetService setService;

    /**
     * 新建优惠券活动
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ApiOperation("新建优惠券活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> couponActivityAdd(@Valid @RequestBody MarketingActivityCouponAddParam addVO) throws SuperCodeException {
        return service.add(addVO);
    }

    @PostMapping("/update")
    @ApiOperation("编辑优惠券活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> couponActivityUpdate(@Valid @RequestBody MarketingActivityCouponUpdateParam updateVo) throws SuperCodeException {
        return updateService.update(updateVo);
    }

    @PostMapping("/copy")
    @ApiOperation("复制优惠券活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> couponActivityUpdateCopy(@Valid @RequestBody MarketingActivityCouponUpdateParam copyVO) throws SuperCodeException {
        return updateService.copy(copyVO);
    }
    @GetMapping("/detail")
    @ApiOperation("优惠券详情")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<MarketingActivityCouponUpdateParam> detail(Long id) throws Exception {
        return service.detail(id);
    }

    @PostMapping("/disOrEnable")
    @ApiOperation("停用或启用")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> disOrEnable(@RequestBody MarketingActivitySetStatusUpdateParam mUpdateStatus) throws Exception {
        return setService.updateActivitySetStatus(mUpdateStatus);
    }


}
