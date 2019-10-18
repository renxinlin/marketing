package com.jgw.supercodeplatform.prizewheels.interfaces;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.config.aop.CheckRole;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.common.Role;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.controller.H5SalerRuleExchangeController;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.H5SalerRuleExchangeDto;
import com.jgw.supercodeplatform.prizewheels.application.service.GetWheelsRewardApplication;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsRewardCdk;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
@Slf4j
@Controller
@RequestMapping("marketing/front/prizeWheels")
@Api(value = "", tags = "大转盘H5")
public class H5WheelsController extends SalerCommonController {

    @Autowired
    private GetWheelsRewardApplication application;
    @CheckRole(role = Role.vip)
    @ResponseBody
    @PostMapping("/reward")
    @ApiOperation(value = "H5领奖", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<WheelsRewardCdk> reward(@Valid @RequestBody PrizeWheelsRewardDto prizeWheelsRewardDto, @ApiIgnore H5LoginVO user) throws CommonException {
        // 暂时只有虚拟wu
        return success(application.reward(prizeWheelsRewardDto,user));
    }


    @GetMapping("/redrict")
    @ApiOperation(value = "码管理重定向", notes = "")
    public String reward(String productBatchId, String outerCodeId, String codeTypeId)   {
        // 扫码重定向到前端 基于产品批次找到活动 TODO 码管理直接重定向到前端
        String url = "redirct:"
                + CallBackConstant.TO_WEB_URL
                + "?productBatchId="+productBatchId
                + "&OuterCodeId="+outerCodeId
                + "&CodeTypeId="+codeTypeId ;
        log.info("==> 大转盘扫码重定向到前端{} ",url);
        return url;
    }


    @ResponseBody
    @GetMapping("/detail")
    @ApiOperation(value = "H5大转盘详情", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult detail(@RequestParam String productBatchId){
        WheelsDetailsVo wheelsDetailsVO = application.detail(productBatchId);
        return success(wheelsDetailsVO);
    }





}
