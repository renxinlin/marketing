package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.mutIntegral.application.service.IntegralRuleApplication;
import com.jgw.supercodeplatform.mutIntegral.application.service.MutiIntegralRecordApplication;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonExportVo;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
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
@RequestMapping("/marketing/mutiIntegral/integralRecord")
@Api(value = "积分记录", tags = "积分记录")
public class MutiIntegralRecordExportController extends SalerCommonController{

    @Autowired
    private MutiIntegralRecordApplication application;



    // 通用表头设置
    static Map<String, String> fieldMap = new HashMap<>();
    static {
        fieldMap.put("memberName","会员昵称");
        fieldMap.put("mobile","手机");
        fieldMap.put("integralNum","积分变动");
        fieldMap.put("integralMoney","红包变动");
        fieldMap.put("integralReason","变动原因");
        fieldMap.put("salerNum","关联导购积分变动");
        fieldMap.put("recommendNum","关联推荐人积分变动");
        fieldMap.put("customerNum","关联门店积分变动");
        fieldMap.put("channelNum","关联渠道积分变动");
        fieldMap.put("salerMoney","关联导购红包变动");
        fieldMap.put("recommendMoney","关联推荐人红包变动");
        fieldMap.put("customerMoney","关联门店红包变动");
        fieldMap.put("channelMoney","关联渠道红包变动");
        fieldMap.put("outerCodeId","码");

    }


    @GetMapping("/export")
    @ApiOperation(value = "积分记录导出", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public void list(DaoSearch daoSearch,HttpServletResponse response) throws ExcelException {
        // execl导出无须遵守设计模型
        daoSearch.setPageSize(Integer.MAX_VALUE);
        daoSearch.setCurrent(1);
        AbstractPageService.PageResults<List<IntegralRecord>> memberMutiIntegralRecordPage = application.getMemberMutiIntegralRecordPage(daoSearch);
        List<IntegralRecord> list = memberMutiIntegralRecordPage.getList();
        if(CollectionUtils.isEmpty(list)){
            ExcelUtils.listToExcel(new ArrayList<>(),fieldMap,"积分记录",response);
        }
        ExcelUtils.listToExcel(list,fieldMap,"积分记录",response);

    }





}

