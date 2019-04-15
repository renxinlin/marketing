package com.jgw.supercodeplatform.marketing.controller.integral;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.integral.*;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/marketing/exchange")
@Api(tags = "积分兑换")
public class IntegralExchangeController extends CommonUtil {
    private static final String UN_SALE_TYPE="0";
    private static final String SALE_TYPE="1";
    // TODO h5商城url
    @Value("https://www.baidu.com?organizationId=")
    private String H5_IMPERIAL_GRADEN_URL ;
    @Autowired
    private IntegralExchangeService integralExchangeService;


    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ApiOperation(value = "积分兑换设置列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralExchangeWebParam>>> list(IntegralExchange integralExchange) throws Exception {
        String organizationId = getOrganizationId();
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败",500);
        }
        integralExchange.setOrganizationId(organizationId);
        AbstractPageService.PageResults<List<IntegralExchange>> objectPageResults = integralExchangeService.listSearchViewLike(integralExchange);

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
            @ApiImplicitParam(paramType="query",value = "【兑换活动状态0上架1手动下架2自动下架】",name="status")})
    public RestResult upperOrlower(@RequestParam("id") Long id,@RequestParam("status") Byte status) throws Exception {
        RestResult  restResult=new RestResult();
        String organizationId = getOrganizationId();
        integralExchangeService.updateStatus(id,organizationId,status);
        return RestResult.success();
    }




    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ApiOperation(value = "兑换详情|【设置】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(paramType="query",value = "兑换对象id",name="id")})
    public RestResult<IntegralExchangeWebParam> detail(@RequestParam("id") Long id) throws Exception {
        String organizationId = getOrganizationId();
        IntegralExchange integralExchange = integralExchangeService.selectById(id,organizationId);
        // 上下架组织下的兑换对象
        IntegralExchangeWebParam integralExchangeVO = modelMapper.map(integralExchange, IntegralExchangeWebParam.class);
        return RestResult.success("success",integralExchangeVO);
    }




    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "兑换|【更新】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult update(@RequestBody IntegralExchangeUpdateParam integralExchange) throws Exception {
         integralExchangeService.updateByOrganizationId(integralExchange,getOrganizationId(),getOrganizationName());
         return RestResult.success();
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "兑换|【新增】", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult add(@RequestBody IntegralExchangeAddParam integralExchange) throws Exception {
        String organizationId = getOrganizationId();
        String organizationName = getOrganizationName();
        integralExchangeService.add(integralExchange, organizationId, organizationName);
        return RestResult.success();
    }




// 抽取到基础信息

//    @RequestMapping(value = "/getProduct",method = RequestMethod.GET)
//    @ApiOperation(value = "获取自卖产品|非自卖产品,调用基础信息平台", notes = "")
//    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
//            @ApiImplicitParam(paramType="type",value = "0非自卖1自卖产品",name="super-token")})
//    public RestResult<List<Map<String,Object>>> getProduct(@RequestParam("type") String type) throws Exception {
//        String organizationId = getOrganizationId();
//        if(SALE_TYPE.equals(type)){
//            // 查询基础平台
//
//            List<Map<String,Object>> saleProducts = null;
////            unsaleProductService.selectPruduct(organizationId);
//            return RestResult.success("success",saleProducts);
//        }else if(UN_SALE_TYPE.equals(type)){
//           List<Map<String,Object>> unsaleProducts = null;
////           unsaleProductService.selectUnsale(organizationId);
//            return RestResult.success("success",unsaleProducts);
//        }
//        return RestResult.error(null);
//
//    }

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
     * 由定时任务实现，待产品确认
     * 或许需要自动下架的兑换数据
     * @param changingStatusList
     * @return
     */
//    private List<IntegralExchange> updateIntegralExchangeWhichNeedChangeStatus(List<IntegralExchange> changingStatusList) {
//        List<IntegralExchange> needChangeList = new ArrayList<IntegralExchange>();
//        List<IntegralExchange> toWebList = new ArrayList<IntegralExchange>();
//
//        for(IntegralExchange integralExchange : changingStatusList){
//            // 自动下架设置0库存为0，1时间范围
//            Byte status = integralExchange.getUndercarriageSetWay();
//            // 库存为零下架
//            if(0 == status && integralExchange.getHaveStock() == 0){
//                // 注意: 这里库存不要求数据一致性，可以存在差错
//                needChangeList.add(integralExchange);
//            }
//            // 由定时任务实现，待产品确认
//
//        }
//        return  toWebList;
//    }



}
