package com.jgw.supercodeplatform.prizewheels.interfaces;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.controller.activity.MarketingSaleMemberRewardController;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.DaoSearchWithOrganizationId;
import com.jgw.supercodeplatform.prizewheels.application.service.WheelsPublishAppication;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ActivityStatus;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import com.jgw.supercodeplatform.user.UserInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("marketing/prizeWheels")
@Api(value = "", tags = "大转盘")
public class WheelsController extends SalerCommonController {
    private static Logger logger = LoggerFactory.getLogger(WheelsController.class);
    @Autowired
    private WheelsPublishAppication appication;

    @Autowired
    protected HttpServletResponse response;

    @Value("{\"userId\":\"用户ID\",\"mobile\":\"手机号\",\"userName\":\"姓名\", \"rewardName\":\"奖项名称\",\"type\":\"奖项类型(虚拟，实物)\", \"address\":\"地址\",\"createTime\":\"领奖时间\", \"address\":\"地址\"}")
    private String EXCEL_FIELD_MAP;

    @PostMapping("/add")
    @ApiOperation(value = "添加", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult add(@Valid  @RequestBody WheelsDto wheelsDto)   {
        appication.publish(wheelsDto);
        return success();
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@Valid  @RequestBody WheelsUpdateDto wheelsDto)   {
        appication.update(wheelsDto);
        return success();
    }


    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<WheelsDetailsVo> detail(@RequestParam("id") Long id)   {
        WheelsDetailsVo wheelsDetailsVo = appication.getWheelsDetails(id);
        return success(wheelsDetailsVo);
    }


    @GetMapping("/delete")
    @ApiOperation(value = "删除", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult delete(@RequestParam("id") Long id)   {
        appication.deletePrizeWheelsById(id);
        return success();
    }



    @PostMapping("/changeStatus")
    @ApiOperation(value = "活动停用启用", notes = "活动状态(1、表示上架进展，0 表示下架)")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult status(@Valid  @RequestBody ActivityStatus activityStatus)   {

        appication.upadteStatus(activityStatus);
        return success();
    }



    @GetMapping("/record")
    @ApiOperation(value = "参与记录", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<WheelsRecordPojo>> > record(DaoSearch daoSearch)   {
        return success(appication.records(daoSearch));
    }


    @GetMapping("/export")
    @ApiOperation(value = "导出参与记录",notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public void exportRecords() throws SuperCodeException {
        DaoSearch daoSearch=new DaoSearch();
        //导出十万条
        daoSearch.setPageSize(100000);
        // step-1 查询记录
        AbstractPageService.PageResults<List<WheelsRecordPojo>> pageResults=appication.records(daoSearch);
        // step-2 获取记录
        List<WheelsRecordPojo> list=pageResults.getList();
        //导出
        Map<String,String> filedMap;
        try {
            filedMap= JsonToMapUtil.toMap(EXCEL_FIELD_MAP);
        } catch (Exception e) {
            logger.error("{desc：记录表头解析异常"+e.getMessage()+"}");
            throw new SuperCodeException("表头解析异常",500);
        }
        ExcelUtils.listToExcel(list, filedMap, "参与记录",response);
    }





}
