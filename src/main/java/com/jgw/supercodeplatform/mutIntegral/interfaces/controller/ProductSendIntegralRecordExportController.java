package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;


import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.mutIntegral.application.service.MutiIntegralRecordApplication;
import com.jgw.supercodeplatform.mutIntegral.application.service.ProductSendIntegralApplication;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductSendIntegral;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分领取记录表 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Controller
@RequestMapping("/marketing/mutiIntegral/productSendIntegralRecord")
@Api(value = "积分派送", tags = "积分派送")
public class ProductSendIntegralRecordExportController extends SalerCommonController{


    @Autowired
    private ProductSendIntegralApplication application;


    // 通用表头设置
    static Map<String, String> fieldMap = new HashMap<>();
    static {
        fieldMap.put("memberName","会员昵称");
        fieldMap.put("memberMobile","手机");
        fieldMap.put("integralNum","注册门店");
        fieldMap.put("integralNum","派送积分值");
        fieldMap.put("operaterName","操作人");
        fieldMap.put("operationTime","操作时间");
        fieldMap.put("remark","备注");
    }


    @GetMapping("/export")
    @ApiOperation(value = "积分派送导出", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public void list(DaoSearch daoSearch,HttpServletResponse response) throws ExcelException {
        // execl导出无须遵守设计模型
        daoSearch.setPageSize(Integer.MAX_VALUE);
        daoSearch.setCurrent(1);

        AbstractPageService.PageResults<List<ProductSendIntegral>> productSendIntegralPageResults = application.sendRecordList(daoSearch);
        List<ProductSendIntegral> list = productSendIntegralPageResults.getList();
        if(CollectionUtils.isEmpty(list)){
            ExcelUtils.listToExcel(new ArrayList<>(),fieldMap,"积分派送记录",response);
        }
        ExcelUtils.listToExcel(list,fieldMap,"积分派送记录",response);

    }





}

