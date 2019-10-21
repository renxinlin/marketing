package com.jgw.supercodeplatform.burypoint.signin.controller;

import com.jgw.supercodeplatform.burypoint.signin.dto.mall.BuryPointMallExchangeTcDto;
import com.jgw.supercodeplatform.burypoint.signin.dto.outerchain.BuryPointOuterChainTcDto;
import com.jgw.supercodeplatform.burypoint.signin.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.signin.dto.reward.BuryPointSignClickTcDto;
import com.jgw.supercodeplatform.burypoint.signin.dto.reward.BuryPointWxMerchantsTcDto;
import com.jgw.supercodeplatform.burypoint.signin.service.*;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @author fangshiping
 * @date 2019/10/16 13:06
 */
@RestController
@RequestMapping("marketing/buryPoint/signin/")
@Api(tags = "签到埋点")
public class SignBuryPointController extends SalerCommonController {
    @Autowired
    private SignBuryPointOuterChainTcService signBuryPointOuterChainTcService;

    @Autowired
    private SignBuryPointRewardTbcService signBuryPointRewardTbcService;

    @Autowired
    private SignBuryPointTemplateTbcService signBuryPointTemplateTbcService;

    @Autowired
    private SignBuryPointWxMerchantsTcService signBuryPointWxMerchantsTcService;

    @Autowired
    private SignBuryPointWxMerchantsTbService signBuryPointWxMerchantsTbService;

    @Autowired
    private SignBuryPointPageViewTcService signBuryPointPageViewTcService;

    @Autowired
    private SignBuryPointMallExchangeTcService signBuryPointMallExchangeTcService;

    @PostMapping(value = "/addOuterChain")
    @ApiOperation(value = "插入C端点击外链埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointOuterChainTc(@RequestBody BuryPointOuterChainTcDto buryPointOuterChainTcDto, @ApiIgnore H5LoginVO user){
        signBuryPointOuterChainTcService.buryPointOuterChainTc(buryPointOuterChainTcDto,user);
        return success();
    }

    @PostMapping(value = "/addReward")
    @ApiOperation(value = "插入C端领取奖励埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointRewardTbc(@RequestBody BuryPointRewardTbcDto buryPointRewardTbcDto, @ApiIgnore H5LoginVO user){
        signBuryPointRewardTbcService.buryPointRewardTbc(buryPointRewardTbcDto,user);
        return success();
    }

    @PostMapping(value = "/addClick")
    @ApiOperation(value = "插入C端点击签到埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointWheelsClickTc(@RequestBody BuryPointSignClickTcDto buryPointSignClickTcDto, @ApiIgnore H5LoginVO user){
        signBuryPointRewardTbcService.buryPointWheelsClickTc(buryPointSignClickTcDto,user);
        return success();
    }

    @PostMapping(value = "/addTemplate")
    @ApiOperation(value = "插入模板使用相关埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointTemplateTbc(@RequestParam String templateId,@ApiIgnore H5LoginVO user){
        signBuryPointTemplateTbcService.buryPointTemplateTbc(templateId,user);
        return success();
    }

    @PostMapping(value = "/addWxTc")
    @ApiOperation(value = "插入C端公众号关注埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointWxMerchantsTc(@RequestBody BuryPointWxMerchantsTcDto buryPointWxMerchantsTcDto, @ApiIgnore H5LoginVO user){
        signBuryPointWxMerchantsTcService.buryPointWxMerchantsTc(buryPointWxMerchantsTcDto,user);
        return success();
    }

    @PostMapping(value = "/addWxTb")
    @ApiOperation(value = "插入B端配置公众号埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointWxMerchantsTb(@RequestParam String WxPicture, @ApiIgnore H5LoginVO user){
        signBuryPointWxMerchantsTbService.buryPointWxMerchantsTb(WxPicture,user);
        return success();
    }

    @PostMapping(value = "/addPv")
    @ApiOperation(value = "插入C端PV埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointPageVisitTc(@RequestParam String device, @ApiIgnore H5LoginVO user){
        signBuryPointPageViewTcService.buryPointPageVisitTc(device,user);
        return success();
    }

    @PostMapping(value = "/addMall")
    @ApiOperation(value = "插入C端商城兑换埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointMallExchangeTc(@RequestBody BuryPointMallExchangeTcDto buryPointMallExchangeTcDto , @ApiIgnore H5LoginVO user){
        signBuryPointMallExchangeTcService.signBuryPointMallExchangeTc(buryPointMallExchangeTcDto,user);
        return success();
    }

}
