package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.mutIntegral.application.service.MutiIntegralRecordApplication;
import com.jgw.supercodeplatform.mutIntegral.application.service.ProductReturnIntegralApplication;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.ProductReturnIntegralService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductReturnIntegral;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Controller
@RequestMapping("/marketing/mutiIntegral/productReturnIntegral")
@Api(value = "会员退货", tags = "会员退货")
public class ProductReturnIntegralExportController extends SalerCommonController{


    @Autowired
    private ProductReturnIntegralApplication application;



    // 通用表头设置
    static Map<String, String> fieldMap = new HashMap<>();
    static {
        fieldMap.put("memberMobile","会员账号");
        fieldMap.put("customerName","注册门店");
        fieldMap.put("productName","产品名称");
        fieldMap.put("returnIntegralNum","退货积分值");
        fieldMap.put("outCodeId","积分码");
        fieldMap.put("reason","退货原因");
        fieldMap.put("totalIntegral","累计积分");
        fieldMap.put("haveIntegral","可用积分");
        fieldMap.put("integralTime","积分时间");
        fieldMap.put("returnTime","退货时间");

    }


    @GetMapping("/export")
    @ApiOperation(value = "导出", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public void list(DaoSearch daoSearch, HttpServletResponse response) throws ExcelException {
        // execl导出无须遵守设计模型
        daoSearch.setPageSize(Integer.MAX_VALUE);
        daoSearch.setCurrent(1);
        AbstractPageService.PageResults<List<ProductReturnIntegral>> listPageResults  = application.returnBackList(daoSearch);
        List<ProductReturnIntegral> list = listPageResults.getList();
        if(CollectionUtils.isEmpty(list)){
            ExcelUtils.listToExcel(new ArrayList<>(),fieldMap,"积分减扣记录",response);
        }
        ExcelUtils.listToExcel(list,fieldMap,"积分减扣记录",response);

    }





}

