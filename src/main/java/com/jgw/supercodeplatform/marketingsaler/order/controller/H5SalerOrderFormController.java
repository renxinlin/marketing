package com.jgw.supercodeplatform.marketingsaler.order.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.base.config.aop.CheckRole;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ColumnnameAndValueListDto;
import com.jgw.supercodeplatform.marketingsaler.order.service.SalerOrderFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 产品积分规则表 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@RestController
@RequestMapping("marketing/h5/salerOrderForm")
@Api(value = "", tags = "H5订货管理")
public class H5SalerOrderFormController extends SalerCommonController {

     @Autowired
    private SalerOrderFormService service;

    @CheckRole(role = "1")
    @PostMapping("/showOrder")
    @ApiOperation(value = "h5订货字段展示", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult showOrder(H5LoginVO user)   {
        return success(service.showOrder(user));
    }


    @CheckRole(role = "1")
    @PostMapping("/saveOrder")
    @ApiOperation(value = "h5订货", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@Valid ColumnnameAndValueListDto columnnameAndValueListDto, H5LoginVO user) throws CommonException {
        service.saveOrder(columnnameAndValueListDto,user);
        return success();
    }


    @CheckRole(role = "1")
    @GetMapping(value = "/getIntegralStatusByH5")
    @ApiOperation(value = "H5查看开启页面按钮", notes = "")
    public RestResult<String> getIntegralStatusByH5(@RequestParam String organizationId) throws Exception {
        String status = redisUtil.get(UserConstants.MARKETING_ORDER_BUTTON + organizationId);
        if( StringUtils.isEmpty(status)){
            // 默认状态
            return success(OpenIntegralStatus.open);
        }else {
            return success(status);

        }
    }



}

