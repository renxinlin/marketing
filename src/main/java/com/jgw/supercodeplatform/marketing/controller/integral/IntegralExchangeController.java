package com.jgw.supercodeplatform.marketing.controller.integral;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.baseservice.vo.ProductAndSkuVo;
import com.jgw.supercodeplatform.marketing.dto.integral.*;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralExchangeService;
import com.jgw.supercodeplatform.marketing.service.integral.UnsaleProductService;
import com.jgw.supercodeplatform.pojo.cache.OrganizationCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/marketing/exchange")
@Api(tags = "积分兑换")
public class IntegralExchangeController extends CommonUtil {
    private static final Byte UN_SALE_TYPE=(byte) 0;
    private static final Byte SALE_TYPE= (byte) 1;
    // TODO h5商城url
    @Value("https://www.baidu.com?organizationId=")
    private String H5_IMPERIAL_GRADEN_URL ;
    @Autowired
    private IntegralExchangeService integralExchangeService;
    @Autowired
    private UnsaleProductService unsaleProductService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ApiOperation(value = "积分兑换设置列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralExchangeWebParam>>> list(DaoSearchWithOrganizationIdParam integralExchange) throws Exception {
        String organizationId = getOrganizationId();
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败",500);
        }
        IntegralExchange integralExchangeParam =modelMapper.map(integralExchange,IntegralExchange.class);
        integralExchangeParam.setOrganizationId(organizationId);
        AbstractPageService.PageResults<List<IntegralExchange>> objectPageResults = integralExchangeService.listSearchViewLike(integralExchangeParam);

        // 转换成VO
        List<IntegralExchange> list = objectPageResults.getList();
        List<IntegralExchangeWebParam> listVO = new ArrayList<>();
        for (IntegralExchange ie : list){
            listVO.add(modelMapper.map(ie,IntegralExchangeWebParam.class));
        }
        AbstractPageService.PageResults<List<IntegralExchangeWebParam>> pagesVO = new  AbstractPageService.PageResults<List<IntegralExchangeWebParam>>(null,objectPageResults.getPagination());
        pagesVO.setList(listVO);
        pagesVO.setOther(objectPageResults.getOther());
        return RestResult.success("success", pagesVO);
    }



    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value = "删除积分兑换设置", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id")})
    public RestResult deleteProduct(@RequestParam("id") Long id) throws Exception {
        String organizationId = getOrganizationId();
        // 删除组织下的对象
        integralExchangeService.deleteByOrganizationId(id,organizationId);
        return RestResult.success();
    }





    @RequestMapping(value = "/upperOrlower",method = RequestMethod.GET)
    @ApiOperation(value = "上下架设置", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id"),
            @ApiImplicitParam(paramType="query",value = "【兑换活动状态3上架1手动下架2自动下架】",name="status")})
    public RestResult upperOrlower(@RequestParam("id") Long id,@RequestParam("status") Byte status) throws Exception {
        String organizationId = getOrganizationId();
        integralExchangeService.updateStatus(id,organizationId,status);
        return RestResult.success();
    }




    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ApiOperation(value = "兑换|【详情】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id")})
    public RestResult<IntegralExchangeWebParam> detail(@RequestParam("id") Long id) throws Exception {
        String organizationId = getOrganizationId();
        IntegralExchange integralExchange = integralExchangeService.selectById(id,organizationId);
        // 上下架组织下的兑换对象
        IntegralExchangeWebParam integralExchangeVO = modelMapper.map(integralExchange, IntegralExchangeWebParam.class);
        String productId = integralExchangeVO.getProductId();
        String skuId = integralExchangeVO.getSkuId();

        // 前端数据格式
        List<String> webTree = new ArrayList<>();
        webTree.add(productId);
        if(!StringUtils.isBlank(skuId)){
            // sku可有可无
            webTree.add(skuId);
        }
        integralExchangeVO.setProducts(webTree);
        return RestResult.success("success",integralExchangeVO);
    }




    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "兑换|【更新】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult update(@Valid  @RequestBody IntegralExchangeUpdateParam integralExchange) throws Exception {
        integralExchange.setMemberType((byte)0);
         integralExchangeService.updateByOrganizationId(integralExchange,getOrganizationId(),getOrganizationName());
         return RestResult.success();
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "兑换|【新增】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult add(@Valid @RequestBody IntegralExchangeAddParam integralExchange) throws Exception {


        // 业务流程
        String organizationId = getOrganizationId();
        String organizationName = getOrganizationName();
        integralExchangeService.add(integralExchange, organizationId, organizationName);
        return RestResult.success();
    }





    @RequestMapping(value = "/getProduct",method = RequestMethod.GET)
    @ApiOperation(value = "获取自卖产品|非自卖产品,调用基础信息平台，根据是否传递兑换记录id判断是新增还是编辑", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="type",value = "0非自卖1自卖产品",name="super-token")})
    public RestResult< AbstractPageService.PageResults<List<ProductAndSkuVo>> > getProductList(ProductPageParam pageParam) throws Exception {
        String organizationId = getOrganizationId();
        if(SALE_TYPE.intValue() == pageParam.getType().intValue()){
            // 查询基础信息自卖产品
            return  unsaleProductService.selectSalePruduct(organizationId, pageParam);
        }else if(UN_SALE_TYPE.intValue() == pageParam.getType().intValue()){
            // 查询基础信息非自卖产品
            return unsaleProductService.selectUnSalePruduct(organizationId, pageParam);
        }
        return RestResult.error(null);
    }

    @RequestMapping(value = "/promotion",method = RequestMethod.GET)
    @ApiOperation(value = "积分商城推广", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult<PromotionParam> promotion() throws Exception {
        PromotionParam promotion = new PromotionParam();

        OrganizationCache organization = getOrganization();
        // 企业名称
        String organizationFullName = organization.getOrganizationFullName();
        // 企业id
        String organizationId = organization.getOrganizationId();
        // 企业头像
        String logo = organization.getLogo();
        promotion.setLogo(logo);
        promotion.setOrganizationId(organizationId);
        promotion.setOrganizationName(organizationFullName);
        promotion.setUrl(H5_IMPERIAL_GRADEN_URL + organizationId);
        // 上下架组织下的兑换对象
        return RestResult.success("success",promotion);
    }


    /**
     * 存在的问题，基础数据删除时，调用营销接口失败，是回滚还是要保证数据的最终一致性
     * 基础反调上层服务是否合理
     *
     * @param productId
     * @return
     * @throws SuperCodeException
     */

    @RequestMapping(value = "/deleteProduct",method = RequestMethod.GET)
    @ApiOperation(value = "兑换删除产品【基础平台删除产品】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
             @ApiImplicitParam(paramType="query",value = "productId",name="productId")})
    public RestResult delete(String productId) throws SuperCodeException {
        String organizationId = getOrganizationId();
        integralExchangeService.deleteByBaseService(organizationId,productId,null,false);
        // 上下架组织下的兑换对象
        return RestResult.success("success",null);
    }

    @RequestMapping(value = "/deleteSkuBybase",method = RequestMethod.GET)
    @ApiOperation(value = "兑换删除sku【基础平台删除产品】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "productId",name="productId"),
            @ApiImplicitParam(paramType="query",value = "skuId 字符串类型",name="skuId")})
    public RestResult delete(String productId,String skuId) throws SuperCodeException {
        String organizationId = getOrganizationId();
        integralExchangeService.deleteByBaseService(organizationId,productId,skuId,true);
        // 上下架组织下的兑换对象
        return RestResult.success("success",null);
    }








}
