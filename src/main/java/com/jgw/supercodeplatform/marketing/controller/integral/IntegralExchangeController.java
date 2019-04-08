package com.jgw.supercodeplatform.marketing.controller.integral;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralExchangeService;
import com.jgw.supercodeplatform.marketing.service.integral.UnsaleProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/marketing/exchange")
@Api(tags = "积分兑换")
public class IntegralExchangeController extends CommonUtil {
    private static final String UN_SALE_TYPE="0";
    private static final String SALE_TYPE="1";
    @Autowired
    private IntegralExchangeService integralExchangeService;


    @Autowired
    private UnsaleProductService unsaleProductService;

    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ApiOperation(value = "积分兑换设置列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralExchange>>> list(@RequestBody IntegralExchange integralExchange) throws Exception {
        String organizationId = getOrganizationId();
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败",500);
        }
         AbstractPageService.PageResults<List<IntegralExchange>> objectPageResults = integralExchangeService.listSearchViewLike(integralExchange);
        return RestResult.success("success", objectPageResults);
    }


    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value = "删除积分兑换设置", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id")})
    public RestResult deleteProduct(@RequestParam("id") Long id) throws Exception {

        String organizationId = getOrganizationId();
                if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败",500);
        }
        if(id != null && id <= 0){
            throw new SuperCodeException("id不合法",500);
        }
        // 删除组织下的对象
        integralExchangeService.deleteByOrganizationId(id,organizationId);
        return RestResult.success();
    }





    @RequestMapping(value = "/upperOrlower",method = RequestMethod.GET)
    @ApiOperation(value = "上下架设置", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id"),
            @ApiImplicitParam(paramType="query",value = "【兑换活动状态0上架1手动下架2自动下架】",name="status")})
    public RestResult upperOrlower(@RequestParam("id") Long id,@RequestParam("status") Byte status) throws Exception {
        RestResult  restResult=new RestResult();
        // TODO 自动下架后能否上架
        // 如果已经上架，则不上架
        // 如果自动下架【是否能上架,目前能】数据不能越权
        String organizationId = getOrganizationId();
        integralExchangeService.updateStatus(id,organizationId,status);
        return RestResult.success();
    }




    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ApiOperation(value = "兑换详情|【设置】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id")})
    public RestResult<IntegralExchange> detail(@RequestParam("id") Long id) throws Exception {
        String organizationId = getOrganizationId();
        IntegralExchange integralExchange = integralExchangeService.selectById(id,organizationId);
        // 上下架组织下的兑换对象
        // TODO 自动下架后能否上架
        return RestResult.success("success",integralExchange);
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "兑换详情|【更新】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult<IntegralExchange> update(@RequestBody IntegralExchange integralExchange) throws Exception {
        RestResult<IntegralExchange>  restResult=new RestResult();
        integralExchangeService.updateByOrganizationId(integralExchange,getOrganizationId());
         return restResult;
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "兑换详情|【更新】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult<IntegralExchange> add(@RequestBody IntegralExchange integralExchange) throws Exception {
        RestResult<IntegralExchange>  restResult=new RestResult();
        integralExchange.setOrganizationId(getOrganizationId());
        integralExchangeService.add(integralExchange);
        return restResult;
    }




    // TODO 自动下架后能否上架 前端希望两个接口
    //////////////////积分推广////////////////////////


    @RequestMapping(value = "/getProduct",method = RequestMethod.GET)
    @ApiOperation(value = "获取自卖产品|非自卖产品", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="type",value = "0非自卖1自卖产品",name="super-token")})
    public RestResult<List<Map<String,Object>>> getProduct(@RequestParam("type") String type) throws Exception {
        String organizationId = getOrganizationId();
        if(SALE_TYPE.equals(type)){
            // 查询基础平台

            List<Map<String,Object>> saleProducts = unsaleProductService.selectPruduct(organizationId);
            return RestResult.success("success",saleProducts);
        }else if(UN_SALE_TYPE.equals(type)){
           List<Map<String,Object>> unsaleProducts =  unsaleProductService.selectUnsale(organizationId);
            return RestResult.success("success",unsaleProducts);
        }
        return RestResult.error(null);

    }

    // 积分推广


    // 自动下架



}
