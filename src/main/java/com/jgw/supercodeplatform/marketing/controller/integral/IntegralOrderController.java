package com.jgw.supercodeplatform.marketing.controller.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralOrderPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralOrderExcelService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 兑换订单
 */
@RestController
@RequestMapping("/marketing/order")
@Api(tags = "订单记录")
public class IntegralOrderController extends CommonUtil {
    @Autowired
    private IntegralOrderService integralOrderService;
    @Autowired
    private IntegralOrderExcelService  integralOrderExcelService;

    @Value("{\"orderId\":\"订单号\",\"productName\":\"产品\",\"skuName\":\"sku\", \"exchangeNum\":\"兑换数量\",\"exchangeIntegralNum\":\"兑换积分\",\"name\":\"收货姓名\", \"mobile\":\"收货手机\",\"address\":\"收货地址\",\"createDateStr\":\"兑换时间\",\"statusName\":\"状态\"}")
    private String MARKET_Integral_EXCEL_FIELD_MAP;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 积分记录列表
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "兑换订单记录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralOrderPageParam>>> list(DaoSearchWithOrganizationIdParam integralOrder) throws Exception {
        String organizationId = getOrganizationId();
        IntegralOrder order = modelMapper.map(integralOrder,IntegralOrder.class);
        order.setOrganizationId(organizationId);
        AbstractPageService.PageResults pageResults = integralOrderService.listSearchViewLike(order);
        return RestResult.success("success", pageResults);
    }


    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation(value = "导出订单记录", notes = "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", value = "新平台token--开发联调使用", name = "super-token"),
            @ApiImplicitParam(paramType = "query", value = "查询参数", name = "search")
    })
    public void excelWithOrganization(String search) throws Exception {
        //        // step-1: 参数设置
        IntegralOrder searchParams =  new  IntegralOrder();
        String organizationId = getOrganizationId();
        searchParams.setOrganizationId(organizationId);
        searchParams.setStartNumber(1);
        searchParams.setPageSize(Integer.MAX_VALUE);
        searchParams.setSearch(search);
        // step-2: 获取结果
        AbstractPageService.PageResults<List<IntegralOrderPageParam>> pageResults = integralOrderExcelService.listSearchViewLike(searchParams);
        List<IntegralOrderPageParam> list = pageResults.getList();
        // step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
        Map filedMap = null;
        try {
            filedMap = JsonToMapUtil.toMap(MARKET_Integral_EXCEL_FIELD_MAP);
        } catch (Exception e) {
            throw new SuperCodeException("营销中奖记录表头解析异常", 500);
        }
        // step-4: 导出前端
        ExcelUtils.listToExcel(list, filedMap, "中奖记录", response);

    }


    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    @ApiOperation(value = "订单发货", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult delivery(@RequestParam("orderId") Long orderId) throws Exception {
        integralOrderService.updateDeliveryStatus(orderId,getOrganizationId());
        return RestResult.success();
    }
}
