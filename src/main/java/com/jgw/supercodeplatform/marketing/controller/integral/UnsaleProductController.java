package com.jgw.supercodeplatform.marketing.controller.integral;


import com.alibaba.fastjson.JSONArray;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.integral.ProductUnsaleParam;
import com.jgw.supercodeplatform.marketing.dto.integral.SkuInfo;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import com.jgw.supercodeplatform.marketing.service.integral.UnsaleProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/marketing/unsale-product")
@Api(tags = "非自卖产品")
public class UnsaleProductController extends CommonUtil {
    // 列表，详情，添加，更新，删除
    @Autowired
    private UnsaleProductService unsaleProductService;
    @Autowired
    private ModelMapper modelmapper;
    /**
     *  积分记录列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ApiOperation(value = "获取非自卖列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<ProductUnsaleParam>>> list(ProductUnsale productUnsale) throws Exception {
        productUnsale.setOrganizationId(getOrganizationId());
        AbstractPageService.PageResults<List<ProductUnsale>> objectPageResults = unsaleProductService.listSearchViewLike(productUnsale);
        // DO转VO
        List<ProductUnsale> listDO = objectPageResults.getList();
        List<ProductUnsaleParam> listVOs = new ArrayList<>();
        for(ProductUnsale productUnsaleDO: listDO){
            ProductUnsaleParam productUnsaleVO = modelmapper.map(productUnsaleDO, ProductUnsaleParam.class);
            // 处理sku信息
            if (productUnsaleDO.getUnsaleProductSkuNum() != 0){
                String skuJsonString = productUnsaleDO.getUnsaleProductSkuInfo();
                List<SkuInfo> skuChilds = JSONArray.parseArray(skuJsonString, SkuInfo.class);
                // 列表展示
                productUnsaleVO.setSkuNameFirst(skuChilds.get(0).getSkuName());
                // 鼠标悬浮
                StringBuffer sb = new StringBuffer("");

                for(SkuInfo skuInfo : skuChilds){
                    sb.append(",").append(skuInfo.getSkuName());
                }
                productUnsaleVO.setSkuNames(sb.toString().replace(",",""));
            }
            listVOs.add(productUnsaleVO);
        }
        AbstractPageService.PageResults<List<ProductUnsaleParam>> resultVO = new AbstractPageService.PageResults<List<ProductUnsaleParam>>(null,objectPageResults.getPagination());
        resultVO.setPagination(objectPageResults.getPagination());
        resultVO.setOther(objectPageResults.getOther());
        resultVO.setList(listVOs);
        return RestResult.success("success",resultVO);
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(value = "获取非自卖详情", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult get(@RequestParam("id") Long id) throws Exception {
        ProductUnsale productUnsale = unsaleProductService.selectById(id,getOrganizationId());
        return RestResult.success("success",productUnsale);
    }



    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增非自卖产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult add(@RequestBody ProductUnsale productUnsale) throws Exception {
        productUnsale.setOrganizationId(getOrganizationId());
        productUnsale.setOrganizationName(getOrganizationName());
        productUnsale.setCreateUserId(getUserLoginCache().getUserId());
        productUnsale.setCreateUserName(getUserLoginCache().getUserName());
        int i =  unsaleProductService.add(productUnsale);
        return RestResult.success();
    }



    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新非自卖产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@RequestBody ProductUnsale productUnsale) throws Exception {
        productUnsale.setUpdateUserId(getUserLoginCache().getUserId());
        productUnsale.setUpdateUserName(getUserLoginCache().getUserName());
        productUnsale.setOrganizationId(getOrganizationId());
        productUnsale.setOrganizationName(getOrganizationName());
        unsaleProductService.update(productUnsale);
        return RestResult.success();
    }



    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value = "删除非自卖产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult deleteProduct(@RequestParam("id") Long id) throws Exception {
        String organizationId = getOrganizationId();
        unsaleProductService.delete(id,organizationId);
        return RestResult.success();
    }

}
