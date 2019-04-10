package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/marketing/h5/exchange")
@Api(tags = "H5会员积分兑换")
public class MemberExchangeController {

    @Autowired
    private IntegralExchangeService integralExchangeService;




    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ApiOperation(value = "积分兑换设置列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<List<IntegralExchange>> list(@RequestParam("organizationId") String organizationId) throws Exception {
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败",500);
        }
        List<IntegralExchange> results = integralExchangeService.getOrganizationExchange(organizationId);
        // 处理当前兑换的积分下架：【经过评定自动下架采用查询后更新的方式】
//        List<IntegralExchange> changingStatusList = objectPageResults.getList();
//        List<IntegralExchange> changedStatusList = updateIntegralExchangeWhichNeedChangeStatus(changingStatusList);
//        objectPageResults.setList(changedStatusList);
        return RestResult.success("success", results);
    }



    @RequestMapping(value = "detailByMember",method = RequestMethod.GET)
    @ApiOperation(value = "兑换详情|【h5会员】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id")})
    public RestResult<IntegralExchange> detailByMember(@RequestParam("id") Long id) throws Exception {
        IntegralExchange integralExchange = integralExchangeService.selectById(id);
        return RestResult.success("success",integralExchange);
    }



}
