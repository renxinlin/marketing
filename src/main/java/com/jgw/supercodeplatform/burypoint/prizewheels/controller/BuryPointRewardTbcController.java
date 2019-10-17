package com.jgw.supercodeplatform.burypoint.prizewheels.controller;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.service.BuryPointRewardTbcService;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author fangshiping
 * @date 2019/10/16 14:10
 */
@RestController
@RequestMapping("marketing/prizeWheels/buryPoint")
@Api(tags = "大转盘发放奖励埋点")
public class BuryPointRewardTbcController extends SalerCommonController {
    @Autowired
    private BuryPointRewardTbcService buryPointRewardTbcService;

    @PostMapping("/addReward")
    @ApiOperation(value = "插入b端领取奖励埋点数据")
    public RestResult buryPointRewardTbc(@RequestBody BuryPointRewardTbcDto buryPointRewardTbcDto){
        buryPointRewardTbcService.buryPointRewardTbc(buryPointRewardTbcDto);
        return success();
    }

}
