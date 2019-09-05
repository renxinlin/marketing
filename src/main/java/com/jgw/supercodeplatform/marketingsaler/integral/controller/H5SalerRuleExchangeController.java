package com.jgw.supercodeplatform.marketingsaler.integral.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.config.aop.CheckRole;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.common.Role;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.H5SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.service.H5SalerRuleExchangeService;
import com.jgw.supercodeplatform.marketingsaler.integral.service.SalerRuleExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/h5salerRuleExchange")
@Api(value = "", tags = "h5销售员积分兑换")
public class H5SalerRuleExchangeController extends SalerCommonController {

    @Autowired
    private H5SalerRuleExchangeService service;
    @CheckRole(role = Role.salerRole)
    @PostMapping("/save")
    @ApiOperation(value = "兑换", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult exchange(@Valid @RequestBody H5SalerRuleExchangeDto salerRuleExchangeDto, H5LoginVO user) throws CommonException {
        service.exchange(salerRuleExchangeDto,user);
        return success();
    }



    @PostMapping("/list")
    @ApiOperation(value = "兑换列表", notes = "")
    public RestResult list(@Valid @RequestBody H5SalerRuleExchangeDto salerRuleExchangeDto, H5LoginVO user) throws CommonException {
        return success();
    }




    @CheckRole(role = Role.salerRole)
    @PostMapping("/reward")
    @ApiOperation(value = "积分领取", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult reward(@Valid @RequestBody H5SalerRuleExchangeDto salerRuleExchangeDto, H5LoginVO user) throws CommonException {

        return success();
    }


    @CheckRole(role = Role.salerRole)
    @PostMapping("/record")
    @ApiOperation(value = "积分记录", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult record() throws CommonException {

        return success();
    }




    @GetMapping(value = "/getIntegralStatusByH5")
    @ApiOperation(value = "H5查看开启页面领积分按钮", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getIntegralStatusByH5(@RequestParam String organizationId) throws Exception {
        String status = redisUtil.get(UserConstants.MARKETING_SALER_INTEGRAL_BUTTON + organizationId);
        if( StringUtils.isEmpty(status)){
            // 默认状态
            return success(OpenIntegralStatus.open);
        }else {
            return success(status);

        }
    }



}

