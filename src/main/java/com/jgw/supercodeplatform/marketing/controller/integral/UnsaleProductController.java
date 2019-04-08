package com.jgw.supercodeplatform.marketing.controller.integral;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marketing/unsale-product")
@Api(tags = "非自卖产品")
public class UnsaleProductController {
    // 列表，详情，添加，更新，删除


    /**
     *  积分记录列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ApiOperation(value = "积分记录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<ProductUnsale>>> list(@RequestBody ProductUnsale integralRule) throws Exception {
        RestResult<AbstractPageService.PageResults<List<ProductUnsale>>> restResult=new RestResult<AbstractPageService.PageResults<List<ProductUnsale>>>();

        return restResult;
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(value = "获取非自卖详情", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult get(@RequestParam("id") Long id) throws Exception {

        RestResult  restResult=new RestResult();
        return restResult;
    }



    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增非自卖产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult add(@RequestBody ProductUnsale productUnsale) throws Exception {

        RestResult  restResult=new RestResult();
        return restResult;
    }



    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新非自卖产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@RequestBody ProductUnsale productUnsale) throws Exception {

        RestResult  restResult=new RestResult();
        return restResult;
    }



    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value = "删除非自卖产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult deleteProduct(@RequestParam("id") Long id) throws Exception {

        RestResult  restResult=new RestResult();
        return restResult;
    }

}
