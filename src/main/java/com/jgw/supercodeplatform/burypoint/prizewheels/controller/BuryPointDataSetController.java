package com.jgw.supercodeplatform.burypoint.prizewheels.controller;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain.BuryPointOuterChainTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointWheelsClickTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointWxMerchantsTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.service.set.*;
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
@RequestMapping("marketing/buryPoint/prizeWheels/")
@Api(tags = "大转盘埋点")
public class BuryPointDataSetController extends SalerCommonController {
    @Autowired
    private BuryPointOuterChainTcService buryPointOuterChainTcService;

    @Autowired
    private BuryPointRewardTbcService buryPointRewardTbcService;

    @Autowired
    private BuryPointTemplateTbService buryPointTemplateTbService;

    @Autowired
    private BuryPointWxMerchantsTcService buryPointWxMerchantsTcService;

    @Autowired
    private BuryPointWxMerchantsTbService buryPointWxMerchantsTbService;

    @Autowired
    private BuryPointPageViewTcService buryPointPageViewTcService;

    @Autowired
    private BuryPointTemplateTcService buryPointTemplateTcService;

    @PostMapping(value = "/addOuterChain")
    @ApiOperation(value = "插入C端点击外链埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointOuterChainTc(@RequestBody BuryPointOuterChainTcDto buryPointOuterChainTcDto,@ApiIgnore H5LoginVO user){
        buryPointOuterChainTcService.buryPointOuterChainTc(buryPointOuterChainTcDto,user);
        return success();
    }

    @PostMapping(value = "/addReward")
    @ApiOperation(value = "插入C端领取奖励埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointRewardTbc(@RequestBody BuryPointRewardTbcDto buryPointRewardTbcDto,@ApiIgnore H5LoginVO user){
        buryPointRewardTbcService.buryPointRewardTbc(buryPointRewardTbcDto,user);
        return success();
    }

    @PostMapping(value = "/addClick")
    @ApiOperation(value = "插入C端点击大转盘埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointWheelsClickTc(@RequestBody BuryPointWheelsClickTcDto buryPointWheelsClickTcDto, @ApiIgnore H5LoginVO user){
        buryPointRewardTbcService.buryPointWheelsClickTc(buryPointWheelsClickTcDto,user);
        return success();
    }

    @PostMapping(value = "/addBTemplate")
    @ApiOperation(value = "B端配置模板相关埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointTemplateTbc(@RequestParam String templateId,@ApiIgnore H5LoginVO user){
        buryPointTemplateTbService.buryPointTemplateTb(templateId,user);
        return success();
    }

    @PostMapping(value = "/addCTemplate")
    @ApiOperation(value = "C端扫描模板相关埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointTemplateTc(@RequestParam String templateId,@ApiIgnore H5LoginVO user){
        buryPointTemplateTcService.buryPointTemplateTc(templateId,user);
        return success();
    }

    @PostMapping(value = "/addWxTc")
    @ApiOperation(value = "C端公众号关注埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointWxMerchantsTc(@RequestBody BuryPointWxMerchantsTcDto buryPointWxMerchantsTcDto, @ApiIgnore H5LoginVO user){
        buryPointWxMerchantsTcService.buryPointWxMerchantsTc(buryPointWxMerchantsTcDto,user);
        return success();
    }

    @PostMapping(value = "/addWxTb")
    @ApiOperation(value = "B端配置公众号埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointWxMerchantsTb(@RequestParam String WxPicture, @ApiIgnore H5LoginVO user){
        buryPointWxMerchantsTbService.buryPointWxMerchantsTb(WxPicture,user);
        return success();
    }

    @PostMapping(value = "/addPv")
    @ApiOperation(value = "C端PV埋点数据")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointPageVisitTc(@RequestParam String device, @ApiIgnore H5LoginVO user){
        buryPointPageViewTcService.buryPointPageVisitTc(device,user);
        return success();
    }

}
