package com.jgw.supercodeplatform.marketingsaler.order.controller;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.order.dto.*;
import com.jgw.supercodeplatform.marketingsaler.order.pojo.SalerOrderForm;
import com.jgw.supercodeplatform.marketingsaler.order.service.SalerOrderFormService;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品积分规则表 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@RestController
@RequestMapping("marketing/salerOrderForm")
@Api(value = "", tags = "订货管理")
public class SalerOrderFormController extends SalerCommonController {

     @Autowired
    private SalerOrderFormService service;

    @PostMapping("/save")
    @ApiOperation(value = "设置表单", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@Valid @RequestBody SalerOrderFormSettingListDto salerOrderFormDtos) throws SuperCodeException {
        service.alterOrCreateTableAndUpdateMetadata(salerOrderFormDtos.getSalerOrderFormSettingDto());
        return success();
    }


    @GetMapping("/detail")
    @ApiOperation(value = "获取表单详情", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult getById()   {
        return success(service.detail());
    }


    @GetMapping("/pageHeader")
    @ApiOperation(value = "获取分页表头", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult pageHeader()   {
        return success(service.detail());
    }

    /**
     * 动态表分页
     * @param daoSearch
     * @return
     * @throws CommonException
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取动态表单分页数据", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<Map<String,Object>>>>  list(DaoSearch daoSearch) throws SuperCodeException {
        // todo处理表不存在
        try {
            return success(service.selectPage(daoSearch));
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.error("获取数据失败",null,500);
        }
    }





    @GetMapping(value = "/openIntegralStatus")
    @ApiOperation(value = "开启关闭页面按钮 1开启2 关闭", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> openIntegralStatus(String status) throws Exception {
        Asserts.check(!StringUtils.isEmpty(status)
                && (OpenIntegralStatus.close.equals(status)  || OpenIntegralStatus.open.equals(status))  ,"状态不合法");
        redisUtil.set(UserConstants.MARKETING_ORDER_BUTTON+commonUtil.getOrganizationId(),status);
        return success();
    }




    @GetMapping(value = "/getIntegralStatus")
    @ApiOperation(value = "查看开启页面按钮", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getIntegralStatus() throws Exception {
        String status = redisUtil.get(UserConstants.MARKETING_ORDER_BUTTON + commonUtil.getOrganizationId());
        if( StringUtils.isEmpty(status)){
            // 默认状态
            return success(OpenIntegralStatus.open);
        }else {
            return success(status);

        }

    }



    @GetMapping("/updateStatus")
    @ApiOperation(value = "10月21需求 更新状态0未发货1发货", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult updateStatus(@RequestParam Long id,@RequestParam byte status) throws SuperCodeException {
        service.updateStatus(id,status);
        return success();
    }


    @GetMapping("/delete")
    @ApiOperation(value = "10月21需求 删除订单", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult delete(@RequestParam Long id) throws SuperCodeException {
        service.delete(id);
        return success();
    }


    @GetMapping("/detailbyId")
    @ApiOperation(value = "10月21需求 根据id查看详情", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult detailbyId(@RequestParam Long id) throws SuperCodeException {

        return success(service.detailbyId(id));
    }




    @PostMapping("/saveOrder")
    @ApiOperation(value = "10月21需求新增订单", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult saveOrder(@Valid @RequestBody ColumnnameAndValueListNoValidDto columnnameAndValueListDto) throws SuperCodeException {
        List<ColumnnameAndValueNoValidDto> datas = columnnameAndValueListDto.getDatas();
        List<ColumnnameAndValueDto> list = new ArrayList<>();
        datas.forEach(e->{
            if(!StringUtils.isEmpty(e.getColumnName())&& !StringUtils.isEmpty(e.getColumnValue())){
                ColumnnameAndValueDto columnnameAndValueDto = modelMapper.map(e, ColumnnameAndValueDto.class);
                list.add(columnnameAndValueDto);
            }
        });
        service.saveOrder(list);
        return success();
    }


    @PostMapping("/updateOrder")
    @ApiOperation(value = "10月21需求 修改更新订单", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult updateOrder(@Valid @RequestBody ColumnnameAndValueListNoValidDto columnnameAndValueListDto) throws SuperCodeException {
        List<ColumnnameAndValueNoValidDto> datas = columnnameAndValueListDto.getDatas();
        List<ColumnnameAndValueDto> list = new ArrayList<>();
        datas.forEach(e->{
            if(!StringUtils.isEmpty(e.getColumnName())){
                ColumnnameAndValueDto columnnameAndValueDto = modelMapper.map(e, ColumnnameAndValueDto.class);
                if(StringUtils.isEmpty(columnnameAndValueDto.getColumnValue())){
                    columnnameAndValueDto.setColumnValue(CallBackConstant.EMPTY);
                }
                list.add(columnnameAndValueDto);
            }

        });
        service.updateOrder(list);
        return success();
    }



}

