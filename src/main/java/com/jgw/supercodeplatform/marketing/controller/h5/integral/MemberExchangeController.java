package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.integral.*;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralExchangeService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequestMapping("/marketing/h5/exchange")
@Api(tags = "H5会员积分兑换")
public class MemberExchangeController {

    @Autowired
    private IntegralExchangeService integralExchangeService;




    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "积分兑换设置列表", notes = "")
     public RestResult<List<IntegralExchangeParam>> list(@RequestParam("organizationId") String organizationId) throws Exception {
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败",500);
        }
        List<IntegralExchangeParam> results = integralExchangeService.getOrganizationExchange(organizationId);
        return RestResult.success("success", results);
    }



    @RequestMapping(value = "detailByMember",method = RequestMethod.GET)
    @ApiOperation(value = "兑换详情|【h5会员】", notes = "")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",value = "产品ID",name="productId")})
    public RestResult<IntegralExchangeDetailParam> detailByMember(@RequestParam("productId") String productId) throws Exception {
        IntegralExchangeDetailParam integralExchange = integralExchangeService.selectById(productId);
        return RestResult.success("success",integralExchange);
    }


    @RequestMapping(value = "detailSkuByMember",method = RequestMethod.GET)
    @ApiOperation(value = "兑换详情SKU+地址信息|【h5会员】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult<IntegralExchangeSkuDetailAndAddress> detailSkuByMember(@RequestParam("productId") String productId, @ApiIgnore H5LoginVO jwtUser) throws Exception {
        return RestResult.success("success",integralExchangeService.detailSkuByMember(productId,jwtUser.getMemberId()));
    }


    @RequestMapping(value = "exchanging",method = RequestMethod.POST)
    @ApiOperation(value = "商品兑换", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "会员请求头",name="jwt-token")})
    public RestResult<IntegralExchangeSkuDetailAndAddress> exchanging(@RequestBody ExchangeProductParam exchangeProductParam, @ApiIgnore H5LoginVO jwtUser) throws Exception {
        integralExchangeService.exchanging(exchangeProductParam);
        return RestResult.success("success",null);
    }






}
