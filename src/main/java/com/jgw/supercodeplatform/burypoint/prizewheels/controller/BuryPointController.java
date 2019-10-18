package com.jgw.supercodeplatform.burypoint.prizewheels.controller;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain.BuryPointOuterChainTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointWheelsClickTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.service.BuryPointOuterChainTcService;
import com.jgw.supercodeplatform.burypoint.prizewheels.service.BuryPointRewardTbcService;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BuryPointController extends SalerCommonController {
    @Autowired
    private BuryPointOuterChainTcService buryPointOuterChainTcService;

    @Autowired
    private BuryPointRewardTbcService buryPointRewardTbcService;

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


}
